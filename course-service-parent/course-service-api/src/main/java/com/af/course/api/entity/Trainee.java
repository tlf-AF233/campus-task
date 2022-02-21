package com.af.course.api.entity;

import com.af.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生-课程关系
 *
 * @author Tanglinfeng
 * @date 2022/2/20 21:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trainee extends BaseEntity<Trainee> {

    private String courseId;
    private String userId;
}
