package com.mas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test/")
public class TestController {
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("hello")
    public String test(){
        redisTemplate.opsForHash().put("redisHash","stock",10);
        return "hello word!";
    }


}
