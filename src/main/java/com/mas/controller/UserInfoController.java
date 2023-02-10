package com.mas.controller;

import com.mas.model.param.UserInfoPageParam;
import com.mas.model.vo.UserInfoVO;
import com.mas.mybatis.Pager;
import com.mas.service.UserInfoService;
import com.mas.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户信息")
@RestController("/user/")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("获取用户信息")
    @PostMapping("page")
    public Result<Pager<UserInfoVO>> page(@RequestBody UserInfoPageParam pageParam){
        return Result.success(userInfoService.page(pageParam));
    }
}
