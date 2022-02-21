package com.af.course.api.vo;

import com.af.common.vo.BasePageQuery;
import lombok.Data;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 21:29
 */
@Data
public class CourseQueryDto extends BasePageQuery {

    private List<String> collegeIdList;
    private String courseSearchKey;
}
