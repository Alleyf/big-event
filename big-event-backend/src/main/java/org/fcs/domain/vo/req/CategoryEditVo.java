package org.fcs.domain.vo.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @Author alleyf
 * @Date 2023/12/29 13:03
 * @Version 1.0
 */
@Data
public class CategoryEditVo {
    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空")
    private Integer id;
    /**
     * 分类名称
     */
    @NotEmpty(message = "分类名称不能为空")
    @Length(min = 2, max = 10)
    private String categoryName;
    /**
     * 分类别名
     */
    @NotEmpty(message = "分类别名不能为空")
    @Length(min = 1, max = 10)
    private String categoryAlias;
    /**
     * 更新者
     */
    @JsonIgnore
    private String updateBy;
}
