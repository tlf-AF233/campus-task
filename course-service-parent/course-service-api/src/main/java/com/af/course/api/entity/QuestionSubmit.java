package com.af.course.api.entity;

import com.af.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 提交记录
 *
 * @author Tanglinfeng
 * @date 2022/2/26 17:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSubmit extends BaseEntity<QuestionSubmit> {

    private String courseId;
    private String learningId;
    private String userId;
    private Integer isPassed;
    private Integer score;
    private String submitContent;
}
