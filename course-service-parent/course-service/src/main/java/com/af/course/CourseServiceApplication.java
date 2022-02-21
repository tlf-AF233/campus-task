package com.af.course;

import com.af.common.constant.CommonConstants;
import com.af.user.api.feign.UserServiceClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Tanglinfeng
 * @date 2022/2/7 17:19
 */
@SpringBootApplication(scanBasePackages = CommonConstants.BASE_PACKAGE)
@EnableFeignClients(basePackages = CommonConstants.BASE_PACKAGE)
public class CourseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseServiceApplication.class, args);
    }
}
