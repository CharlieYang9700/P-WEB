package com.mas.config;

import com.mas.annotation.Login;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class LoginInterceptor implements HandlerInterceptor {

    static final ThreadLocal<Object> USER_THREAD = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;
            this.verifyLogin(request,method);
        }
        return true;
    }


    private void verifyLogin(HttpServletRequest request,HandlerMethod method) throws Exception {
        Login login = method.getMethod().getDeclaredAnnotation(Login.class);

        if(Objects.isNull(login)){
            return;
        }
        String authorization = request.getHeader("authorization");
        if(StringUtils.isEmpty(authorization)){
            throw new Exception("没有携带token");
        }
    }
}
