package com.imooc.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApiIgnore
@Controller
public class SSOController {
    final static  Logger logger = LoggerFactory.getLogger(SSOController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/login")
    @ResponseBody
    public Object login(String returnUrl, Model model,
                        HttpServletRequest request,
                        HttpServletResponse response){
        model.addAttribute("returnUrl", returnUrl);
        //TODO 后续完善校验是否登录
        // 用户从未登录过，第一次进入则跳转至CAS的统一登录页面

        return "测试完成";
    }
}
