package com.imooc.controller.center;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "用户信息接口",tags = {"用户信息相关接口"})
@RequestMapping("userInfo")
public class CenterUserController {
    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "修改用户信息" ,notes = "修改用户信息" ,httpMethod =  "POST")
    @PostMapping("update")
    public IMOOCJSONResult update(
            @ApiParam(name = "userId" ,value = "用户id" )
                    String userId,
            @RequestBody @Valid CenterUserBO centerUserBO,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) {
        //判断BindingResult 是否保存错误的验证信息，如果有，直接return
        if (result.hasErrors()){
            Map<String, String> error = getError(result);
            return IMOOCJSONResult.errorMap(error);
        }


        Users userResult =  centerUserService.updateUserInfo(userId, centerUserBO);

        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        //TODO 后续要改，增加令牌token,会整合进redis
        return IMOOCJSONResult.ok(userResult);
    }



    private Users setNullProperty(Users userResult){
        userResult.setPassword(null);
        userResult.setPassword(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

    private Map<String ,String> getError(BindingResult result){
        List<FieldError> fieldErrors = result.getFieldErrors();
        Map<String,String> map = new HashMap<>();
        for (FieldError error : fieldErrors) {
            //发生验证错误所对应的某一个属性
            String errorField = error.getField();
            // 验证错误的信息
            String errorMsg = error.getDefaultMessage();
            map.put(errorField, errorMsg);
        }
        return map;
    }}
