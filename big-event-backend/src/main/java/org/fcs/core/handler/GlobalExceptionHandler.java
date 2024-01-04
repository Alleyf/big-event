package org.fcs.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.fcs.model.pojo.Result;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
@SuppressWarnings("all")
public class GlobalExceptionHandler {
    /**
     * 处理异常的方法
     *
     * @param e 异常对象
     * @return 返回结果对象
     */


    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result handleException(BindException e) {
        /*拿到@NotNull,@NotBlank和 @NotEmpty等注解上的message值 */
        String msg = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return Result.error(StringUtils.hasLength(msg) ? msg : "操作失败");
    }

    /* MethodArgumentNotValidException则是Spring MVC框架中的一个异常类型，它表示在控制器方法中使用@Valid注解对请求参数进行校验时，某个请求参数不符合指定的校验规则，这通常也是由于参数格式不正确、数据类型不匹配等原因导致的。当使用Spring MVC框架进行Web开发时，通常会遇到MethodArgumentNotValidException异常。 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result handleException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException：", e);
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String msg = allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        return Result.error(StringUtils.hasLength(msg) ? msg : "操作失败");
    }

    @ExceptionHandler(SQLException.class)
    public Result handleException(SQLException e) {
        log.error("SQLException：", e);
        return Result.error(StringUtils.hasLength(e.getLocalizedMessage()) ? e.getLocalizedMessage() : "操作失败");
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result handleException(SQLIntegrityConstraintViolationException e) {
        log.error("SQLIntegrityConstraintViolationException：", e);
        return Result.error(StringUtils.hasLength(e.getCause().getLocalizedMessage()) ? e.getCause().getLocalizedMessage() : "操作失败");
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("Exception：", e);
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
