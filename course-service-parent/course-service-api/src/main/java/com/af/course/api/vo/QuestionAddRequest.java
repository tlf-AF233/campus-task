package com.af.course.api.vo;

import lombok.Data;

/**
 * 添加题目
 *
 * @author Tanglinfeng
 * @date 2022/2/21 22:28
 */
@Data
public class QuestionAddRequest {

    private String courseId;
    private String questionId;
    private String questionName;
    private String items;
    private String questionAnalysis;
    private Integer score;
    private Integer difficulty;
    private String questionAnswer;
    private String questionType;
}
