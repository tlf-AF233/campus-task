package com.af.user.api.vo;

import com.af.user.api.entity.College;
import com.af.user.api.entity.Major;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/19 0:37
 */
@Data
public class StudentVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String name;

    private String phone;

    private String email;

    private Integer sex;

    private String password;

    private String unionId;

    private String openId;

    private String avatarUrl;

    /**
     * 学号
     */
    private String studentId;

    private College college;

    private Major major;
}
