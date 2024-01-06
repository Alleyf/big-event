package org.fcs.service;

import org.fcs.domain.vo.req.UserPwdVo;
import org.fcs.model.entity.User;

/**
 * @Author alleyf
 * @Date 2023/12/24 23:03
 * @Version 1.0
 */
public interface UserService {
    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    User findByUsername(String username);

    /**
     * 注册新用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 注册成功返回字符串"注册成功"
     */
    String register(String username, String password);

    Integer update(User user);

    Integer updateAvatar(Integer uid, String avatar);

    Integer updatePassword(UserPwdVo userPwdVo);
}
