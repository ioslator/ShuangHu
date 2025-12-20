package com.shaunghu_hrms.shuanghu.common;

public class Result<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(true, "操作成功", data);
    }
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(true, msg, data);
    }
    public static <T> Result<T> error(String msg) {
        return new Result<>(false, msg, null);
    }

    public Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}