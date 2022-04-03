package com.af.user.controller;

import com.af.common.constant.TokenConstants;
import com.af.common.exceptions.BusinessException;
import com.af.common.model.ResponseBean;
import com.af.common.utils.PageUtil;
import com.af.common.vo.PageVO;
import com.af.user.api.constant.UserResultCode;
import com.af.user.api.entity.User;
import com.af.user.api.vo.*;
import com.af.user.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/8 16:32
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation("注册用户")
    @PostMapping("/register")
    public ResponseBean<Boolean> registerUser(@RequestBody RegisterDto registerDto) {

        if (userService.existEmail(registerDto.getEmail())) {
            throw new BusinessException(UserResultCode.USER_EMAIL_EXIST);
        } else if (userService.existStudentId(registerDto.getStudentId())) {
            throw new BusinessException(UserResultCode.USER_STUDENT_ID_EXIST);
        } else if (userService.existPhone(registerDto.getPhone())) {
            throw new BusinessException(UserResultCode.USER_PHONE_EXIST);
        }

        return new ResponseBean<>(userService.registerUser(registerDto));
    }

    @ApiOperation("查询学生")
    @PostMapping("/search/student")
    public ResponseBean<PageVO<StudentVo>> findStudentInfo(@RequestBody StudentQueryDto studentQueryDto) {
        PageInfo<StudentVo> pageInfo = PageUtil.pageInfo(studentQueryDto.getPageNum(), studentQueryDto.getPageSize());
        return new ResponseBean<>(userService.findStudentList(pageInfo, studentQueryDto));
    }

    @ApiOperation("删除")
    @DeleteMapping("/deleteUser")
    public ResponseBean<Boolean> deleteUser(@RequestParam("id") Long id) {
        return new ResponseBean<>(userService.deleteById(id));
    }

    @ApiOperation("查看个人信息")
    @PostMapping("/space")
    public ResponseBean<UserVo> myInfo(@RequestHeader(TokenConstants.TOKEN_HEADER) String token) {
        return new ResponseBean<>(userService.findMyInfo(token));
    }

    @ApiOperation("更新个人信息")
    @PutMapping("/update")
    public ResponseBean<Boolean> updateUser(@RequestBody User user) {
        return new ResponseBean<>(userService.update(user));
    }

    @ApiOperation("更新密码")
    @PutMapping("/updatePassword")
    public ResponseBean<Boolean> updatePassword(@RequestHeader(TokenConstants.TOKEN_HEADER) String token,
                                                @RequestParam("oldPassword") String oldPassword,
                                                @RequestParam("newPassword") String newPassword) {
        return new ResponseBean<>(userService.updatePwd(token, oldPassword, newPassword));
    }

    /**
     * 用户登录
     * @param loginRequest
     * @return
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseBean<LoginVo> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseBean<>(userService.login(loginRequest));
    }

    @ApiOperation("查找单个用户")
    @GetMapping("/{id}")
    public ResponseBean<User> findUserById(@PathVariable("id") Long id) {
        return new ResponseBean<>(userService.getById(id));
    }

    @ApiOperation("查询用户列表")
    @GetMapping("/listByIds")
    public ResponseBean<List<User>> findUserListByIds(@RequestParam("ids") List<String> ids) {
        Long[] longIds = new Long[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            longIds[i] = Long.parseLong(ids.get(i));
        }
        return new ResponseBean<>(userService.findListById(longIds));
    }
}
