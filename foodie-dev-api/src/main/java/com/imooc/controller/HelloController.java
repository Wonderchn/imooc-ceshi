package com.imooc.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class  HelloController {
    final static  Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/hello")
    public Object hello(){
        //key为string类型，值为Integer类型
        redisTemplate.opsForValue().set("test",1);

        redisTemplate.opsForValue().set("hello","小鸟");
        return "测试完成";
    }
}
