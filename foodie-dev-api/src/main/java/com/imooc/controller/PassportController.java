package com.imooc.controller;


import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.IMOOCJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("passport")
public class PassportController {
    @Autowired
    private UserService userService;


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



    @PostMapping("/regist")
    public IMOOCJSONResult usernameIsExist(@RequestBody UserBO userBo){
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

}
