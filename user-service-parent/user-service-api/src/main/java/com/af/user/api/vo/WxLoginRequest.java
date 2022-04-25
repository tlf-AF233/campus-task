package com.af.user.api.vo;

import lombok.Data;

/**
 * @author Tanglinfeng
 * @date 2022/4/16 15:32
 */
@Data
public class WxLoginRequest {

    private String username;
    private String password;
    private String code;
}
