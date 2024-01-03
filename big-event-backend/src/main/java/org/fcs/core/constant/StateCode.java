package org.fcs.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author alleyf
 * @Date 2023/12/24 22:32
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StateCode {
    SUCCESS(200, "success"),
    FAIL(500, "fail"),
    NOT_FOUND(404, "未找到"),
    BAD_REQUEST(400, "error"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "禁止访问"),
    NOT_IMPLEMENTED(501, "未实现"),
    SERVICE_UNAVAILABLE(503, "服务不可用");

    private Integer code;
    private String message;

//    ResponseCode(Integer code, String message) {
//        this.code = code;
//        this.message = message;
//    }
}
