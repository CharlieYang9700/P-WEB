package com.mas.utils;

/**
 * 返回结果集
 * @param <T>
 */
public class Result <T>{
    private int code;
    private String errorCode;
    private String errorMsg;
    private T data;
    // 提示信息（兼容以前往外提供的接口）
    private String message;

    public Result(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.message = errorMsg;
        this.code = 1;
    }

    public Result(T data) {
        this.data = data;
        this.code = 0;
    }

    public static Result error(String message, String errorMsg) {
        return new Result("100", message);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }
}
