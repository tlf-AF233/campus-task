package com.af.course.api.entity;

import com.af.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tanglinfeng
 * @date 2022/3/6 18:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDetail extends BaseEntity<WorkDetail> {

    private String userId;
    private String courseId;
    private String lessonId;
    private String status;
    private String learningTitle;
}
