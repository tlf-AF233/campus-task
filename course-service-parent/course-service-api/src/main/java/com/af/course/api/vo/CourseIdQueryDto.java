package com.af.course.api.vo;

import com.af.common.vo.BasePageQuery;
import lombok.Data;

/**
 * @author Tanglinfeng
 * @date 2022/2/21 17:08
 */
@Data
public class CourseIdQueryDto extends BasePageQuery {

    private String courseId;
}
