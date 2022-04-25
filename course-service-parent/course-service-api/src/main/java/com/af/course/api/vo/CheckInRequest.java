package com.af.course.api.vo;

import lombok.Data;

/**
 * @author Tanglinfeng
 * @date 2022/4/22 13:23
 */
@Data
public class CheckInRequest {

    private String courseId;

    private String checkInCode;
}
