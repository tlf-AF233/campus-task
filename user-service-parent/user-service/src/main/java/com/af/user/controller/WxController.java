package com.af.user.controller;

import com.af.common.constant.DefaultResultCode;
import com.af.common.constant.TokenConstants;
import com.af.common.model.ResponseBean;
import com.af.common.utils.JwtUtil;
import com.af.user.api.constant.UserResultCode;
import com.af.user.api.entity.User;
import com.af.user.api.vo.LoginRequest;
import com.af.user.api.vo.LoginVo;
import com.af.user.api.vo.WxLoginRequest;
import com.af.user.service.UserService;
import com.af.user.service.WxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author Tanglinfeng
 * @date 2022/4/16 14:45
 */
@Api(tags = "微信相关接口")
@RestController
@AllArgsConstructor
@RequestMapping("/v1/wx")
public class WxController {

    private final WxService wxService;

    private final UserService userService;

    @ApiOperation("绑定openId")
    @PostMapping("/bind")
    public ResponseBean<LoginVo> bind(@RequestBody WxLoginRequest wxLoginRequest){
        LoginRequest loginRequest = new LoginRequest();
        BeanUtils.copyProperties(wxLoginRequest, loginRequest);
        loginRequest.setRoleCode("STUDENT");
        LoginVo loginVo = userService.login(loginRequest);
        Long userId = JwtUtil.getUserId(loginVo.getToken());
        User user = new User();
        user.setId(userId);
        user.setOpenId(wxService.getOpenId(wxLoginRequest.getCode()));

        userService.update(user);

        return new ResponseBean<>(loginVo);
    }

    @ApiOperation("用户是否绑定openId")
    @PostMapping("/checkBind")
    public ResponseBean<LoginVo> checkBind(String code) {
        String openId = wxService.getOpenId(code);
        if (openId == null) {
            return ResponseBean.fail(DefaultResultCode.ERROR);
        }
        User user = new User();
        user.setOpenId(openId);
        User userEntity = userService.get(user);
        // 用户未绑定
        if (userEntity == null) {
            return new ResponseBean<>(UserResultCode.NOT_BIND_OPENID, LoginVo.builder().build());
        }

        // 生成token
        String token = JwtUtil.createToken(new HashMap<String, Object>() {{
            put(TokenConstants.CLAIMS_ROLE, String.join(",", userEntity.getRoleList()));
            put(TokenConstants.CLAIMS_USER_ID, userEntity.getId());
        }});

        LoginVo loginVo = LoginVo.builder()
                .token(token)
                .avatarUrl(userEntity.getAvatarUrl())
                .name(userEntity.getName())
                .roleList(userEntity.getRoleList())
                .build();

        return new ResponseBean<>(loginVo);
    }
}
