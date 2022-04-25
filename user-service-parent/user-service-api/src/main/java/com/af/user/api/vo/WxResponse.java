package com.af.user.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Tanglinfeng
 * @date 2022/4/16 14:39
 */
@Data
public class WxResponse implements Serializable {

    private String openid;
    private String session_key;
}
