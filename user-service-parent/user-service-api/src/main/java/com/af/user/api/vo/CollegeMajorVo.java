package com.af.user.api.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/18 15:34
 */
@Data
public class CollegeMajorVo {

    private String collegeId;
    private String name;

    private List<MajorVo> majorVoList;
}
