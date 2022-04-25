package com.af.user.service;

import com.af.common.utils.LogUtil;
import com.af.user.api.vo.WxResponse;
import com.af.user.config.WxProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Tanglinfeng
 * @date 2022/4/16 14:29
 */
@Slf4j
@Service
public class WxService {

    private final String openIdUrl = "https://api.weixin.qq.com/sns/jscode2session?appid={appId}&secret={appSecret}&js_code={code}&grant_type=authorization_code";

    private final RestTemplate restTemplate;

    private final WxProperties wxProperties;

    public String getOpenId(String code) {
        LogUtil.info(log, "微信临时授权码：{}", code);
        ResponseEntity<WxResponse> responseEntity = restTemplate.getForEntity(openIdUrl, WxResponse.class, wxProperties.getAppId(), wxProperties.getAppSecret(), code);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String openId = responseEntity.getBody().getOpenid();
            LogUtil.info(log, "用户openId：{}", openId);

            return openId;
        } else {
            LogUtil.info(log, "微信请求openId接口失败,code::{}", code);
            return null;
        }
    }


    public WxService(final RestTemplate restTemplate,
                     final WxProperties wxProperties) {
        this.restTemplate = restTemplate;
        this.wxProperties = wxProperties;
    }
}
