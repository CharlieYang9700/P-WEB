package com.mas.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis/")
@Api(tags = {"redis测试服务"})
public class RedisTestController {
/*    @Autowired
    private RedissonClient redissonClient;

    public Result reentrantLock(String id){
        RLock lock = redissonClient.getLock("lock");
        try {
            lock.lock();
            System.out.println();

        }finally {
            lock.unlock();
        }

        return null;
    }*/
}
