package com.af.course.api.entity;

import com.af.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 15:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lesson extends BaseEntity<Lesson> {

    private String lessonId;
    private String courseId;
    private String lessonName;
    private Integer lessonNumber;
}
