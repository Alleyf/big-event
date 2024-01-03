package org.fcs.core.annotation;

/**
 * @Author alleyf
 * @Date 2023/12/29 20:49
 * @Version 1.0
 */

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.fcs.core.validation.StateValidation;

import java.lang.annotation.*;

@Documented// 元注解：说明该注解是文档注释
@Constraint(validatedBy = {StateValidation.class}) // 元注解：说明该注解是校验注解，需要指定校验器
@Target({ElementType.FIELD}) // 元注解：说明该注解可以用在哪些地方，FIELD表示可以用在属性上
@Retention(RetentionPolicy.RUNTIME) // 元注解：说明该注解在什么级别上可用，RUNTIME表示在运行时有效
public @interface State {
    /**
     * 校验失败时返回的错误信息
     *
     * @return 错误信息
     */
    String message() default "{state参数的值只能是已发布或草稿}";

    /**
     * 校验分组
     *
     * @return 分组
     */
    Class<?>[] groups() default {};

    /**
     * 负载
     *
     * @return 获取到State注解的附加信息
     */
    Class<? extends Payload>[] payload() default {};
}
