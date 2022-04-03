package com.af.course.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author Tanglinfeng
 * @date 2022/3/27 0:44
 */
@Data
@Builder
public class StudentStudyDetail {

    private String studentId;
    private String name;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishDate;
}
