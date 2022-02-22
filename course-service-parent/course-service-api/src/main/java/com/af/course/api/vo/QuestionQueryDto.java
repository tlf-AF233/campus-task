package com.af.course.api.vo;

import com.af.common.vo.BasePageQuery;
import lombok.Data;

/**
 * @author Tanglinfeng
 * @date 2022/2/22 15:01
 */
@Data
public class QuestionQueryDto extends BasePageQuery {

    private String courseId;
    private String questionId;
    private String questionType;
}
