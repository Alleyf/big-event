package org.fcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author alleyf
 * @Date 2023/12/24 17:26
 * @Version 1.0
 */
@SpringBootApplication
@EnableConfigurationProperties
public class BigEventApplication {
    /**
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(BigEventApplication.class, args);
    }
}
