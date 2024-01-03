package org.fcs.utils;

import cn.hutool.jwt.JWTUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.fcs.model.entity.User;

import java.io.Serial;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author alleyf
 * @Date 2023/12/26 0:54
 * @Version 1.0
 */
//@ConfigurationProperties(prefix = "jwt")
//@Component
@Slf4j
public class JwtTest {
    private static String secret = "qazwsxedcrfv";
    private static Long expire = 1000 * 60 * 60 * 12L;

    //    @Test
    public void createJwtToken() {
        User user = User.builder().id(1).username("alleyf").build();
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
                put("expire_time", System.currentTimeMillis() + expire);
            }
        };

        // 使用claims映射和密钥创建JWT令牌
        log.info("JWT令牌：{}", JWTUtil.createToken(claims, secret.getBytes()));

    }

    //    @Test
    public void parseToken() {
//        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3MDM2MDIxMzh9.Y6FXkyOfoCl0CJd53_iqI3D6acxyK81b7UjvAY7sM_s";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHBpcmVfdGltZSI6MTcwMzYwMjk0MzQxNiwiaWQiOjEsInVzZXJuYW1lIjoiYWxsZXlmIn0.cw6142Ix4xHpYrM_Ol_VeNljw63oyIOKTjK_oFZiqkE";
        if (JWTUtil.verify(token, secret.getBytes())) {
            cn.hutool.jwt.JWT jwt = JWTUtil.parseToken(token);
            System.out.println(jwt.getHeaders());
            System.out.println(jwt.getPayloads());
        }
    }

    //    java-jwt用法
//    @Test
    public void testGen() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "张三");
//生成jWt的代码
        String token = JWT.create()
                .withClaim("user", claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + expire))
                .sign(Algorithm.HMAC256(secret));//指定算法，配置秘钥
        System.out.println(token);
    }

    //    @Test
    public void parseJwtToken() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3MDM2MDIxMzh9.Y6FXkyOfoCl0CJd53_iqI3D6acxyK81b7UjvAY7sM_s";
        // 解析JWT令牌
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Claim claim = decodedJWT.getClaim("user");
        log.info("JWT令牌：{}", claim.asMap());
    }
}
