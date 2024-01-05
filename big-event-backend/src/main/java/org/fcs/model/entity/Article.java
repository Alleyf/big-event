package org.fcs.model.entity;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.fcs.core.annotation.State;
import org.fcs.model.pojo.BaseEntity;
import org.hibernate.validator.constraints.URL;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author alleyf
 */
@Data
@Builder
public class Article extends BaseEntity {
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 文章标题
     */
    @Pattern(regexp = "^\\S{2,20}$")
    @NotEmpty
    private String title;
    /**
     * 文章内容
     */
    @NotEmpty
    private String content;
    /**
     * 封面图像
     */
//    @Pattern(regexp = "^http[s]?:\\/\\/.*", message = "封面图像格式不正确")
    @URL
    @NotEmpty
    private String coverImg;
    /**
     * 发布状态 已发布|草稿
     */

    @State
    private String state;
    /**
     * 文章分类id
     */
    @NotNull
    private Integer categoryId;
}
