package com.af.course.api.entity;

import com.af.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Tanglinfeng
 * @date 2022/2/26 16:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Learning extends BaseEntity<Learning> {

    private String courseId;
    private String lessonId;
    private String learningTitle;
    private String questionId;
    private Integer number;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date limitDate;

}
