package org.fcs.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.fcs.model.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author alleyf
 * @Date 2023/12/25 16:13
 * @Version 1.0
 */

/**
 * 全局异常处理类
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理异常的方法
     *
     * @param e 异常对象
     * @return 返回结果对象
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
