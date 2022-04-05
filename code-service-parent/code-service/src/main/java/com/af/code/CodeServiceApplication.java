package com.af.code;

import com.af.common.constant.CommonConstants;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = CommonConstants.BASE_PACKAGE, exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, MybatisAutoConfiguration.class})
@EnableFeignClients(basePackages = CommonConstants.BASE_PACKAGE)
public class CodeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeServiceApplication.class, args);
    }
}