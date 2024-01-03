package org.fcs.model.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.Data;
import org.fcs.model.pojo.BaseEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 分类实体类
 *
 * @author alleyf
 */
@Data
@Builder
public class Category extends BaseEntity {
    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = {Edit.class})
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
     * 新增分组
     */
    public interface Add extends Default {
    }

    /**
     * 修改分组
     */
    public interface Edit extends Default {
    }
}
