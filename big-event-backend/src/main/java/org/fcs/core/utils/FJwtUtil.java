package org.fcs.core.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.fcs.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * FJwtUtil工具类
 *
 * @Author alleyf
 * @Date 2023/12/26 0:54
 * @Version 1.0
 */
@Component
@Data
@Slf4j
@ConfigurationProperties(prefix = "jwt")
public class FJwtUtil {
    //    @Value注解的类必须注册为Bean，在注册时自动注入配置参数值，否则无法使用
    @Value("${jwt.expire:43200000}")
    private String expire;
    @Value("${jwt.secret:qazwsxedcrfv}")
    private String secret;

    /**
     * 创建JWT令牌（hutool-jwt用法）
     *
     * @param user 用户对象
     * @return JWT令牌字符串
     */
    public String createJwtToken(User user) {
        // 创建claims映射
        Map<String, Object> claims = new HashMap<>() {
            @Serial
            private static final long serialVersionUID = 1L;

            {
                // 添加用户id到claims映射中
                put("id", user.getId());
                // 添加用户名到claims映射中
                put("username", user.getUsername());
                // 添加过期时间到claims映射中
                put("expire_time", System.currentTimeMillis() + Long.parseLong(expire));
            }
        };

        // 使用claims映射和密钥创建JWT令牌
        //        log.info("JWT令牌：{}", token);
        return JWTUtil.createToken(claims, secret.getBytes());
    }

    public JSONObject parseToken(String token) {
        if (JWTUtil.verify(token, secret.getBytes())) {
            JWT jwt = JWTUtil.parseToken(token);
            return jwt.getPayloads();
        } else {
            return null;
        }
    }
}
