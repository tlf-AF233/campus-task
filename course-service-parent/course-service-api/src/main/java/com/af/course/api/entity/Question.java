package com.af.course.api.entity;

import cn.hutool.json.JSONObject;
import com.af.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 15:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question extends BaseEntity<Question> {

    private String questionId;
    private String questionName;
    private String questionAnalysis;
    private String questionAnswer;
    private Integer score;
    private String courseId;
    private String questionDifficulty;
    private String questionType;
    private JSONObject items;  // 存储选择题、填空题的信息
}
