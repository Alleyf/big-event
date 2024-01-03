package org.fcs.core.interceptor;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.fcs.core.constant.StateCode;
import org.fcs.core.utils.ThreadLocalUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器
 *
 * @Author alleyf
 * @Date 2023/12/26 14:13
 * @Version 1.0
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {


    @Resource
    private StringRedisTemplate redis;

    /**
     * 拦截请求预处理方法
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     * @param handler  处理请求的对象
     * @return 是否预处理成功
     * @throws Exception 异常情况
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        try {
//            从redis中获取相同token，存在且相同则验证通过，否则token过期或有误
            Assert.notNull(redis.opsForValue().get(token), "token已过期");
//            验证token信息
            JWTPayload payload = JWTUtil.parseToken(token).getPayload();
            JSONObject userInfo = payload.getClaimsJson();
//            使用ThreadLocal存储业务数据，进行同一请求（线程）的业务数据共享
            ThreadLocalUtil.set(userInfo);
//            log.info("payload:{}", userInfo);
            return true;
        } catch (Exception e) {
            response.setStatus(StateCode.UNAUTHORIZED.getCode());
            return false;
        }
    }

    /**
     * 请求完成后的处理方法。
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     * @param handler  请求处理对象
     * @param ex       异常对象
     * @throws Exception 抛出异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求完成结束后清空ThreadLocal中的数据，防止内存泄露
        ThreadLocalUtil.remove();
    }


}
