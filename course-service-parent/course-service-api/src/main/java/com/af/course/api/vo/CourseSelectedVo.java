package com.af.course.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 22:15
 */
@Data
@NoArgsConstructor
public class CourseSelectedVo extends CourseVo {

    private boolean selected;

    public CourseSelectedVo(CourseVo courseVo, boolean selected) {
        BeanUtils.copyProperties(courseVo, this);
        this.selected = selected;
    }
}
