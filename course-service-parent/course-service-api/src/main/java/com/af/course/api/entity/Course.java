package com.af.course.api.entity;

import com.af.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 14:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course extends BaseEntity<Course> {

    private String courseId;
    private String collegeId;
    private String courseName;
    private String courseTeacher;
    private String courseTeacherId;
}
