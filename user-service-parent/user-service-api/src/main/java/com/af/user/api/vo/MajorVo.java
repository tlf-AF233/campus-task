package com.af.user.api.vo;

import lombok.Data;

/**
 * @author Tanglinfeng
 * @date 2022/2/18 1:32
 */
@Data
public class MajorVo {

    private Long id;
    private String collegeId;
    private String majorId;
    private String majorName;
    private String classNo;
    private String grade;
}
