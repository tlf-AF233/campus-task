package com.af.user.service;

import cn.hutool.crypto.digest.BCrypt;
import com.af.common.base.BaseService;
import com.af.common.constant.CommonConstants;
import com.af.common.constant.TokenConstants;
import com.af.common.exceptions.BusinessException;
import com.af.common.utils.DateUtil;
import com.af.common.utils.JwtUtil;
import com.af.common.vo.PageVO;
import com.af.user.api.constant.UserResultCode;
import com.af.user.api.entity.User;
import com.af.user.api.entity.UserRole;
import com.af.user.api.vo.*;
import com.af.user.mapper.UserMapper;
import com.af.user.mapper.UserRoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/8 16:30
 */
@Service
@AllArgsConstructor
public class UserService extends BaseService<UserMapper, User> {

    private final UserRoleMapper userRoleMapper;

    /**
     * 查询学生信息
     * @param pageInfo
     * @param studentQueryDto
     * @return
     */
    public PageVO<StudentVo> findStudentList(PageInfo<StudentVo> pageInfo, StudentQueryDto studentQueryDto) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        PageInfo<StudentVo> page = new PageInfo<>(this.mapper.findStudentList(studentQueryDto));

        return new PageVO<>(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), page.getList());
    }

    public UserVo findMyInfo(String token) {
        return this.mapper.findOwnInfo(JwtUtil.getUserId(token));
    }

    /**
     * 修改密码
     * @param token
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public boolean updatePwd(String token, String oldPassword, String newPassword) {
        User user = this.mapper.getById(JwtUtil.getUserId(token));
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw new BusinessException(UserResultCode.OLD_PASSWORD_WRONG);
        }
        user.setPassword(BCrypt.hashpw(newPassword));
        user.setModifyDate(new Date());
        return this.mapper.update(user) > 0;
    }

    /**
     * 注册
     * @param registerDto
     * @return
     */
    @Transactional
    public boolean registerUser(RegisterDto registerDto) {
        User user = new User();
        BeanUtils.copyProperties(registerDto, user);
        user.initEntity();
        // 密码加密
        user.setPassword(BCrypt.hashpw(registerDto.getPassword()));
        // 初始化头像
        user.setAvatarUrl(CommonConstants.DEFAULT_AVATAR_URL);

        // 添加角色信息
        List<UserRole> list = new ArrayList<>();
        registerDto.getRoleIdList().forEach(id -> list.add(new UserRole(user.getId().toString(), id)));
        userRoleMapper.batchInsertUserRole(list);
        return this.mapper.insert(user) > 0;
    }

    /**
     * 登录
     * @param loginRequest
     * @return
     */
    public LoginVo login(LoginRequest loginRequest) {
        User user = new User();
        String username = loginRequest.getUsername();
        if (username.length() == 11) {
            user.setPhone(username);
        } else {
            user.setEmail(username);
        }
        User userResult = this.mapper.get(user);

        // 登录失败
        if (null == userResult
                || !userResult.getRoleList().contains(loginRequest.getRoleCode())
                || !BCrypt.checkpw(loginRequest.getPassword(), userResult.getPassword())) {
            throw new BusinessException(UserResultCode.LOGIN_FAILED);
        }

        // 生成token
        String token = JwtUtil.createToken(new HashMap<String, Object>() {{
            put(TokenConstants.CLAIMS_ROLE, String.join(",", userResult.getRoleList()));
            put(TokenConstants.CLAIMS_USER_ID, userResult.getId());
        }});

        return LoginVo.builder()
                .token(token)
                .avatarUrl(userResult.getAvatarUrl())
                .name(userResult.getName())
                .roleList(userResult.getRoleList())
                .build();
    }

    /**
     * 判断学号是否存在
     * @param studentId
     * @return
     */
    public boolean existStudentId(String studentId) {
        if (StringUtils.isEmpty(studentId)) {
            return false;
        }
        return this.mapper.existStudentId(studentId) == 1;
    }

    /**
     * 判断手机号是否存在
     * @param phone
     * @return
     */
    public boolean existPhone(String phone) {

        return this.mapper.existPhone(phone) == 1;
    }

    /**
     * 判断邮箱是否存在
     * @param email
     * @return
     */
    public boolean existEmail(String email) {

        return this.mapper.existEmail(email) == 1;
    }

}
