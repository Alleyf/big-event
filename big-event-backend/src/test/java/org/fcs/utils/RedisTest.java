package org.fcs.utils;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Author alleyf
 * @Date 2023/12/31 20:24
 * @Version 1.0
 */
//@SpringBootTest //测试方法执行前会先加载spring容器
@Slf4j
public class RedisTest {

    @Resource
    private StringRedisTemplate redis;

    //    @Test
    public void test() {
        redis.opsForValue().set("user:name", "alleyf", 20, TimeUnit.SECONDS);
        log.info("user:name:{}", redis.opsForValue().get("user:name"));
    }
}
