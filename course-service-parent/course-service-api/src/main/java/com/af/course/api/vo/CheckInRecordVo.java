package com.af.course.api.vo;

import com.af.course.api.constant.CheckInStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Tanglinfeng
 * @date 2022/4/22 14:19
 */
@Data
public class CheckInRecordVo {

    private String studentId;
    private String userId;
    private String name;
    private String checkInName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date checkInTime;
    private String status;
    private String courseId;

    private Long times; // 未签到次数

}
