package com.af.user.api.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/10 0:03
 */
@Data
@Builder
public class LoginVo {
    public String name;
    public String avatarUrl;
    public String token;
    public List<String> roleList;
}
