package com.af.course.api.vo;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/22 15:07
 */
@Data
public class QuestionVo {

    private String questionId;
    private String questionName;
    private String questionAnalysis;
    private String questionAnswer;
    private Integer score;
    private String courseId;
    private String questionDifficulty;
    private String questionType;
    private List<QuestionItem> items;  // 存储选择题、填空题的信息

    private String createDate;
}
