package com.mas.config;

import com.alibaba.fastjson.JSON;
import com.mas.annotation.Login;
import com.mas.model.UserInfo;
import com.mas.utils.BizException;
import com.mas.utils.Constants;
import com.mas.utils.JwtUtils;
import com.mas.utils.LocalUser;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws BizException {
        if(handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;
            this.verifyLogin(request,method);
        }
        log.info("已经出来了");
        return true;
    }


    private void verifyLogin(HttpServletRequest request,HandlerMethod method) throws BizException {
        Login login = method.getMethod().getDeclaredAnnotation(Login.class);
        if(Objects.isNull(login)){
            return;
        }
        String token = request.getHeader(Constants.AUTHORIZATION);
        if(StringUtils.isEmpty(token)){
            throw new BizException("没有携带token");
        }
        Long userId = JwtUtils.getUserId(token);
        log.info("userId={}",userId);
        String userJson = redisTemplate.opsForValue().get(String.format("%s_%s", Constants.AUTHORIZATION, token));
        log.info("userJson={}",userJson);
        if(StringUtils.isEmpty(userJson)){
            throw new BizException("登录过期，请重新登录");
        }
        UserInfo userInfo = JSON.parseObject(userJson, UserInfo.class);
        LocalUser.put(userInfo);
        log.info("userInfo={}",userInfo);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        LocalUser.remove();
    }
}
