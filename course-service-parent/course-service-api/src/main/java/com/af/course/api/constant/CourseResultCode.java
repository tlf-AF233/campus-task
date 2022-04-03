package com.af.course.api.constant;

import com.af.common.base.ResultCode;
import lombok.Getter;

/**
 * @author Tanglinfeng
 * @date 2022/2/21 21:05
 */
@Getter
public enum CourseResultCode implements ResultCode {
    LESSON_ID_EXIST(2001, "lessonId已经存在！"),
    QUESTION_ID_EXIST(2002, "questionId已经存在！"),
    LEARNING_TITLE_EXIST(2003, "作业标题已经存在！"),

    ;

    private final int code;
    private final String msg;

    CourseResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
