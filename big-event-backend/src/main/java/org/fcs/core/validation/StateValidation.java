package org.fcs.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.fcs.core.annotation.State;

/**
 * @Author alleyf
 * @Date 2023/12/29 20:56
 * @Version 1.0
 */
public class StateValidation implements ConstraintValidator<State, String> {

    /**
     * 校验
     *
     * @param value                      被校验的值
     * @param constraintValidatorContext 校验上下文
     * @return 校验结果：true-校验通过；false-校验不通过
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        //自定义详细校验规则
        return "已发布".equals(value) || "草稿".equals(value);
    }
}
