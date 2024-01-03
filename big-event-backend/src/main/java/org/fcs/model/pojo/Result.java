package org.fcs.model.pojo;


import org.fcs.core.constant.StateCode;

/**
 * @author alleyf
 */ //统一响应结果
public record Result<T>(
        Integer code,//业务状态码  0-成功  1-失败
        String message,//提示信息
        T data//响应数据
) {
    //快速返回操作成功响应结果(带响应数据)
    public static <T> Result<T> success(T data) {
        return new Result<>(StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMessage(), data);
    }

    //快速返回操作成功响应结果
    public static Result success() {
        return new Result(StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMessage(), null);
    }

    public static Result error(String message) {
        return new Result(StateCode.FAIL.getCode(), message, null);
    }

    public static Result unAuthorized(String message) {
        return new Result(StateCode.UNAUTHORIZED.getCode(), message, null);
    }

    public static Result forbidden(String message) {
        return new Result(StateCode.FORBIDDEN.getCode(), message, null);
    }

    //record类型不支持hu-tool的json转换，将当前对象转换为 JSON 格式的字符串用于返回
//    public String toJsonString() {
//        return JSONUtil.toJsonPrettyStr(this);
//    }
}
