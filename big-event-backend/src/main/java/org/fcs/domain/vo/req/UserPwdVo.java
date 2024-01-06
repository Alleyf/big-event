/**
 * 用户密码对象
 */
package org.fcs.domain.vo.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author alleyf
 * @Date 2023/12/28 17:18
 * @Version 1.0
 */
@Data
public class UserPwdVo implements Serializable {
    /**
     * 用户ID
     */
    @NotNull
    private Integer userId;
    /**
     * 旧密码
     */
    @NotEmpty
    @Pattern(regexp = "^\\S{4,20}$", message = "旧密码格式不正确")
    private String oldPwd;
    /**
     * 新密码
     */
    @NotEmpty
    @Pattern(regexp = "^\\S{4,20}$", message = "新密码格式不正确")
    private String newPwd;
    /**
     * 重复密码
     */
    @NotEmpty
    @Pattern(regexp = "^\\S{4,20}$", message = "重复密码格式不正确")
    private String rePwd;
}
