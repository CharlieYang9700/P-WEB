package com.mas.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(BizException.class)
    public Result bizErrorHandle(BizException ex){
        return Result.error(ex.getErrorCode(),ex.getErrorMsg());
    }
}
