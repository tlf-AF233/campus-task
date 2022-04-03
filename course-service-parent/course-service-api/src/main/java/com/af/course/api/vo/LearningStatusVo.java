package com.af.course.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Tanglinfeng
 * @date 2022/3/3 16:37
 */
@Data
public class LearningStatusVo {

    private String courseId;
    private String lessonName;
    private String lessonId;
    private String learningTitle;
    private String finish;
    private String questionId;
    private Integer number;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date limitDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
}
