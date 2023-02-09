package com.mas.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.logging.Logger;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(BizException.class)
    public Result bizErrorHandle(BizException ex){
        log.info("errorMsg={}",ex.getErrorMsg());
        return Result.error(ex.getErrorCode(),ex.getErrorMsg());
    }
}
