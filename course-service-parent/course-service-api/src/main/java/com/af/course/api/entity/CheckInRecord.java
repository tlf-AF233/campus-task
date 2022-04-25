package com.af.course.api.entity;

import com.af.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Tanglinfeng
 * @date 2022/4/22 13:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInRecord extends BaseEntity<CheckInRecord> {

    private String courseId;

    private String userId;

    private String checkInName;

    private Date checkInTime;

    private String status;
}
