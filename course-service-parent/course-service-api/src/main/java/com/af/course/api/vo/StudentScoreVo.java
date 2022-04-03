package com.af.course.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tanglinfeng
 * @date 2022/3/27 20:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentScoreVo {

    private String userId;
    private String studentId;
    private String name;
    private Integer score;

}
