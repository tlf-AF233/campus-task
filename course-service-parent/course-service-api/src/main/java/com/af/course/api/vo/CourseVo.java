package com.af.course.api.vo;

import lombok.Data;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 21:08
 */
@Data
public class CourseVo {

    private String courseId;
    private String collegeId;
    private String courseName;
    private String courseTeacherId;
    private String courseTeacher;
    private String collegeName;

    private Integer studentNumber;
}
