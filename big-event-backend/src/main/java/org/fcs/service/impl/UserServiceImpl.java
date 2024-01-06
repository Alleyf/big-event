package org.fcs.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import jakarta.annotation.Resource;
import org.fcs.core.utils.ThreadLocalUtil;
import org.fcs.domain.vo.req.UserPwdVo;
import org.fcs.mapper.UserMapper;
import org.fcs.model.entity.User;
import org.fcs.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户服务实现类
 *
 * @Author alleyf
 * @Date 2023/12/24 23:10
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;


    private static String getLoginUserName() {
        // 获取用户信息
        JSONObject userJsonObj = ThreadLocalUtil.get();
        // 获取用户名
        return userJsonObj.getStr("username");
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    /**
     * 注册用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 注册结果
     */
    @Override
    public String register(String username, String password) {
//        密码加密
        String cryptPassword = SecureUtil.md5(password);
        return userMapper.addUser(username, cryptPassword) > 0 ? "注册成功" : "注册失败";
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 更新结果
     */
    @Override
    public Integer update(User user) {
//        获取系统当前时间
        // 获取用户名
        String loginUserName = getLoginUserName();
        user.setUpdateTime(LocalDateTime.now());
        user.setUpdateBy(loginUserName);
        return userMapper.update(user);
    }

    /**
     * 更新用户头像
     *
     * @param uid    用户id
     * @param avatar 头像地址
     * @return 更新结果
     */
    @Override
    public Integer updateAvatar(Integer uid, String avatar) {
        // 获取用户名
        String loginUserName = getLoginUserName();
        return userMapper.updateAvatar(uid, avatar, loginUserName);
    }

    /**
     * 更新用户密码
     *
     * @param userPwdVo 用户密码
     * @return 更新结果
     */
    @Override
    public Integer updatePassword(UserPwdVo userPwdVo) {
        System.out.println(userPwdVo);
        // 获取用户名
        String loginUserName = getLoginUserName();
        String oldPassword = SecureUtil.md5(userPwdVo.getOldPwd());
        User user = userMapper.findByUserId(userPwdVo.getUserId());
//        密码参数校验：新密码不能与旧密码相同，且两次密码输入一致,验证原密码是否正确
        Assert.notEquals(userPwdVo.getNewPwd(), userPwdVo.getOldPwd(), "新密码不能与旧密码相同");
        Assert.equals(userPwdVo.getNewPwd(), userPwdVo.getRePwd(), "两次密码输入不一致");
        Assert.equals(user.getPassword(), oldPassword, "原密码不正确");
        userPwdVo.setNewPwd(SecureUtil.md5(userPwdVo.getNewPwd()));
        System.out.println(userPwdVo);
        return userMapper.updatePassword(userPwdVo, loginUserName);

    }
}
