package com.af.course.api.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Tanglinfeng
 * @date 2022/2/26 16:50
 */
@Data
public class LearningAddRequest {
    private String courseId;
    private String lessonId;
    private String learningTitle;
    private String limitDate;
    private String[] questionIds;
}
