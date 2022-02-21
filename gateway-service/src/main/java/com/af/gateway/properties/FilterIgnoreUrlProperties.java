package com.af.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/14 15:29
 */
@Data
@Component
@ConfigurationProperties(prefix = "ignore")
public class FilterIgnoreUrlProperties {

    private List<String> urls = new ArrayList<>();
}