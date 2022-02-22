package com.af.course.api.vo;

import lombok.Data;

/**
 * 选择题选项
 *
 * @author Tanglinfeng
 * @date 2022/2/22 15:09
 */
@Data
public class QuestionItem {
    // 选项
    private String prefix;
    // 内容
    private String content;
}
