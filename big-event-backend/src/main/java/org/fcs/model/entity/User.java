package org.fcs.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.fcs.model.pojo.BaseEntity;

/**
 * @author alleyf
 */
@Data
@Builder
public class User extends BaseEntity {
    /**
     * 主键ID
     */
    @NotNull
    private Integer id;

    /**
     * 用户名
     */
    @Pattern(regexp = "^\\S{4,16}$", message = "用户名格式不正确")
    private String username;

    /**
     * 密码
     * JsonIgnore：密码不返回，当序列化为json时，不返回该字段
     */
    @Pattern(regexp = "^\\S{4,20}$", message = "密码格式不正确")
    @JsonIgnore
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    @Email
    @NotEmpty
    private String email;

    /**
     * 用户头像地址
     */
    private String userPic;
}
