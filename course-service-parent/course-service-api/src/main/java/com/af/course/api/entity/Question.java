package com.af.course.api.entity;

import cn.hutool.json.JSONObject;
import com.af.common.base.BaseEntity;
import com.af.common.utils.DateUtil;
import com.af.course.api.vo.QuestionVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

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

    public QuestionVo toVo() {
        QuestionVo questionVo = new QuestionVo();
        BeanUtils.copyProperties(this, questionVo, "questionDifficulty", "items", "createDate");

        switch (this.getQuestionDifficulty()) {
            case "EASY":
                questionVo.setQuestionDifficulty("简单");
                break;
            case "MEDIUM":
                questionVo.setQuestionDifficulty("中等");
                break;
            case "DIFFICULTY":
                questionVo.setQuestionDifficulty("困难");
                break;
            default: break;
        }

        questionVo.setItems(this.getItems().get("info", List.class));
        questionVo.setCreateDate(DateUtil.formatDateString(this.getCreateDate()));

        return questionVo;
    }
}
