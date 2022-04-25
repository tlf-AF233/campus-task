package com.af.course.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Tanglinfeng
 * @date 2022/4/10 0:01
 */
@Data
public class StudentMessageVo {

    private String id;
    private String courseId;
    private String courseName;
    private String createUser;
    private String modifyUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;
    private String messageTitle;
    private String messageContent;
}
