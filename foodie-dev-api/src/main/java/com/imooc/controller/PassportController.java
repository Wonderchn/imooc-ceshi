package com.imooc.controller;


import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "注册登录",tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在" , httpMethod = "POST")
    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username){
        //1.判断用户名不能为空
        if (StringUtils.isBlank(username)){
           return IMOOCJSONResult.errorMsg("用户名不能为空");

        }

        //2.查找注册的用户名是 否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist){
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }


        //请求成功
        return IMOOCJSONResult.ok();
    }


    @ApiOperation(value = "注册功能", notes = "注册功能" , httpMethod = "POST")
    @PostMapping("/regist")
    public IMOOCJSONResult regist(@RequestBody UserBO userBo){
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String confirmPassword = userBo.getConfirmPassword();

        // 0.判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
            StringUtils.isBlank(password) ||
            StringUtils.isBlank(confirmPassword)){
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }

        // 1.查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist){
            return  IMOOCJSONResult.errorMsg("用户名已经存在");
        }

        // 2.密码长度不能少于6位
        if (password.length() <6){
            return IMOOCJSONResult.errorMsg("密码的长度不能少于6");
        }

        // 3.判断两次密码是否一致
        if (!password.equals(confirmPassword)){
            return IMOOCJSONResult.errorMsg("密码不一致");
        }

        // 4.实现注册
        userService.createUser(userBo);

        //请求成功
        return IMOOCJSONResult.ok();
    }


    @ApiOperation(value = "用户登录", notes = "用户登录" , httpMethod = "POST")
    @PostMapping("/login")
    public IMOOCJSONResult login (@RequestBody UserBO userBo , HttpServletRequest request , HttpServletResponse response) throws  Exception{
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        
        // 0.判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)){
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }

        // 1.实现登录
        Users userResult = userService.queryUserForLogin(username,
                MD5Utils.getMD5Str(password));

        if (userResult == null){
            return IMOOCJSONResult.errorMsg("用户名或密码不正确");
        }
        setNullProperty(userResult);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult),true);





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

}
