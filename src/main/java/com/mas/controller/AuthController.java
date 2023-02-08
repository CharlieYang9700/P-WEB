package com.mas.controller;


import com.mas.annotation.Login;
import com.mas.model.param.UserLoginParam;
import com.mas.model.vo.UserInfoVO;
import com.mas.service.UserInfoService;
import com.mas.utils.BizException;
import com.mas.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"api","登录服务"})
@RequestMapping("/Login/")
public class AuthController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("from-password")
    public Result<UserInfoVO> loginByUsernameAndPassword(@RequestBody UserLoginParam param) throws BizException {
        return Result.success(userInfoService.loginByPassword(param));
    }

}
