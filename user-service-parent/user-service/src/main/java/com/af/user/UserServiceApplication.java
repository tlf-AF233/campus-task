package com.af.user;

import com.af.common.constant.CommonConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Tanglinfeng
 * @date 2022/2/7 15:55
 */
@SpringBootApplication(scanBasePackages = CommonConstants.BASE_PACKAGE)
@EnableFeignClients(basePackages = CommonConstants.BASE_PACKAGE)
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
