package org.fcs.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.fcs.core.utils.FJwtUtil;
import org.fcs.core.utils.ThreadLocalUtil;
import org.fcs.domain.vo.req.UserPwdVo;
import org.fcs.model.entity.User;
import org.fcs.model.pojo.Result;
import org.fcs.service.UserService;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author alleyf
 * @version 1.0
 * @date 2023/12/24 22:56
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
@Validated
@Slf4j
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private FJwtUtil fJwtUtil;

    @Resource
    private StringRedisTemplate redis;

    @Value("${jwt.expire}")
    private Long expire;

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{4,16}$", message = "用户名格式不正确")
                           @RequestParam("username") String username,
                           @Pattern(regexp = "^\\S{4,20}$", message = "密码格式不正确")
                           @RequestParam("password") String password) {
        // 检查用户名是否已存在
        Assert.isNull(userService.findByUsername(username), "用户名已存在");
//        if (userService.findByUsername(username) == null) {
        // 添加新用户
        String message = userService.register(username, password);
        // 返回成功结果
        return Result.success(message);
//        }
    }

    /**
     * 登录接口
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{4,16}$", message = "用户名格式不正确")
                        @RequestParam("username") String username,
                        @Pattern(regexp = "^\\S{4,20}$", message = "密码格式不正确")
                        @RequestParam("password") String password) {
        // 检查用户名是否已存在
        User user = userService.findByUsername(username);
        if (user != null) {
            // 验证密码
            if (user.getPassword().equals(SecureUtil.md5(password))) {
                // 返回jwt令牌
                String jwtToken = fJwtUtil.createJwtToken(user);
//                存储token到redis中
                redis.opsForValue().set(jwtToken, jwtToken, expire / 1000, TimeUnit.SECONDS);
                return Result.success(jwtToken);
            } else {
                // 返回密码错误错误信息
                return Result.error("密码错误");
            }
        } else {
            // 返回用户名不存在错误信息
            return Result.error("用户名不存在");
        }
    }


    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        // 获取用户信息
        JSONObject userJsonObj = ThreadLocalUtil.get();
        // 获取用户名
        String username = userJsonObj.getStr("username");
        // 获取用户信息
        User user = userService.findByUsername(username);
        // 返回用户信息
        return Result.success(user);
    }

    /**
     * 更新用户信息
     *
     * @param user 更新后的用户信息
     * @return 更新结果
     */
    @PutMapping("/update")
    public Result update(@Validated @RequestBody User user) {
        userService.update(user);
        // 返回用户信息
        return Result.success();
    }

    /**
     * 更新用户头像
     *
     * @param uid    用户ID
     * @param avatar 头像路径
     * @return 更新结果
     */
    @PatchMapping("/updateAvatar/{uid}")
    public Result updateAvatar(@PathVariable("uid") Integer uid, @RequestParam("avatar") @URL @NotEmpty String avatar) {
        userService.updateAvatar(uid, avatar);
        return Result.success();
    }

    /**
     * 更新用户密码
     *
     * @param userPwdVo 用户密码
     * @return 更新结果
     */
    @PatchMapping("/updatePassword")
    public Result updatePassword(@RequestBody @Validated UserPwdVo userPwdVo,
                                 @RequestHeader("Authorization") String token) {
        Integer code = userService.updatePassword(userPwdVo);
        return code > 0 ? Result.success() : Result.error("密码修改失败");
    }

}
