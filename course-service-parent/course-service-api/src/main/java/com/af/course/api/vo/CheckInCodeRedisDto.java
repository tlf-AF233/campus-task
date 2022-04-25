package com.af.course.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 存储在redis中的签到信息
 *
 * @author Tanglinfeng
 * @date 2022/4/22 14:02
 */
@Data
public class CheckInCodeRedisDto implements Serializable {

    private String checkInName;
    private String checkInCode;
}
