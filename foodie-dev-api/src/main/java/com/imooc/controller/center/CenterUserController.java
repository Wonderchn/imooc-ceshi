package com.imooc.controller.center;

import com.imooc.controller.BaseController;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.resources.FileUpload;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.DateUtil;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {
    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;


    @ApiOperation(value = "修改用户头像", notes = "修改用户头像", httpMethod = "POST")
    @PostMapping("uploadFace")
    public IMOOCJSONResult uploadFace(
            @ApiParam(name = "userId", value = "用户id", required = true)
                    String userId,
            @ApiParam(name = "file", value = "用户头像", required = true)
                    MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response) {
        // .sh  .php


        //修改用户头像需要指定头像存储的位置
        String fileSpace = fileUpload.getImageUserFaceLocation();
        //在路径上为每一个用户增加一个userId,用于区分不同用户上传

        // 在路径上为每一个用户增加一个userid，用于区分不同用户上传
        String uploadPathPrefix = File.separator + userId;

        FileOutputStream fileOutputStream = null;
        //开始文件上传
        if (file != null) {

            try {
                //获得文件上传的文件名称
                String fileName = file.getOriginalFilename();

                if (StringUtils.isNotBlank(fileName)) {
                    //文件重命名 imooc-face.png  -> ["imooc-face","png"]
                    String[] fileNameArr = fileName.split("\\.");

                    //获得文件的后缀名
                    String suffix = fileNameArr[fileNameArr.length - 1];
                    if (suffix.equalsIgnoreCase("png") &&
                            suffix.equalsIgnoreCase("jpg") &&
                            suffix.equalsIgnoreCase("jpeg")
                    ) {

                        return  IMOOCJSONResult.errorMsg("图片格式不正确");
                    }
                    // face-{userid}.png
                    // 文件名称重组 覆盖式上传，增量式：额外拼接当前时间
                    String newFileName = "face-" + userId + "." + suffix;


                    // 上传的头像最终保存的位置
                    String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;

                    //用于提供给web服务访问的地址
                    uploadPathPrefix += ("/" + newFileName);

                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null) {
                        //创建文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    //文件输出保存到目录
                    fileOutputStream = new FileOutputStream(outFile);
                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (fileOutputStream != null){
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            return IMOOCJSONResult.errorMsg("文件不能为空");
        }

        //获取图片服务地址
        String imageServeUrl = fileUpload.getImageServerUrl();

        //由于浏览器可能存在缓存的情况，所以在这里，我们需要加上时间戳来保证更新后的图片可以及时刷新
        String finalUserFaceUrl = imageServeUrl + uploadPathPrefix +
                "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
        //更新用户头像到数据库

        Users userResult = centerUserService.updateUserFace(userId, finalUserFaceUrl);


        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        //TODO 后续要改，增加令牌token,会整合进redis

        return IMOOCJSONResult.ok();
    }


    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("update")
    public IMOOCJSONResult update(
            @ApiParam(name = "userId", value = "用户id")
                    String userId,
            @RequestBody @Valid CenterUserBO centerUserBO,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) {
        //判断BindingResult 是否保存错误的验证信息，如果有，直接return
        if (result.hasErrors()) {
            Map<String, String> error = getError(result);
            return IMOOCJSONResult.errorMap(error);
        }


        Users userResult = centerUserService.updateUserInfo(userId, centerUserBO);

        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        //TODO 后续要改，增加令牌token,会整合进redis
        return IMOOCJSONResult.ok(userResult);
    }


    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setPassword(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

    private Map<String, String> getError(BindingResult result) {
        List<FieldError> fieldErrors = result.getFieldErrors();
        Map<String, String> map = new HashMap<>();
        for (FieldError error : fieldErrors) {
            //发生验证错误所对应的某一个属性
            String errorField = error.getField();
            // 验证错误的信息
            String errorMsg = error.getDefaultMessage();
            map.put(errorField, errorMsg);
        }
        return map;
    }
}
