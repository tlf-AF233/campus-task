package com.af.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Tanglinfeng
 * @date 2022/4/16 14:28
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WxProperties {

    private String appId;

    private String appSecret;
}
