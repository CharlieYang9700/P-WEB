package com.mas.controller;


import com.mas.annotation.Login;
import com.mas.model.param.UserLoginParam;
import com.mas.model.param.UserRegisterParam;
import com.mas.model.vo.UserInfoVO;
import com.mas.service.UserInfoService;
import com.mas.utils.BizException;
import com.mas.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Api(tags = {"登录服务"})
@RequestMapping("/login/")
public class AuthController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("register")
    @ApiOperation("用户注册")
    public Result<UserInfoVO> registerByUsernamePassword(@RequestBody @Validated UserRegisterParam registerParam) throws BizException {
        return Result.success(userInfoService.registerByUsernameAndPassword(registerParam));
    }

    @PostMapping("from-password")
    @ApiOperation("用户登陆")
    public Result<UserInfoVO> loginByUsernameAndPassword(@RequestBody UserLoginParam loginParam) throws BizException {
        return Result.success(userInfoService.loginByPassword(loginParam));
    }

}
