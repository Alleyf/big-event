/**
 * 用户密码对象
 */
package org.fcs.domain.vo.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author alleyf
 * @Date 2023/12/28 17:18
 * @Version 1.0
 */
@Data
//public class UserPwd extends BaseEntity implements Serializable {
public class UserPwd implements Serializable {
    /**
     * 用户ID
     */
    @NotNull
    private Integer userId;
    /**
     * 旧密码
     */
    @NotEmpty
    private String oldPwd;
    /**
     * 新密码
     */
    @NotEmpty
    private String newPwd;
    /**
     * 重复密码
     */
    @NotEmpty
    private String rePwd;
}
