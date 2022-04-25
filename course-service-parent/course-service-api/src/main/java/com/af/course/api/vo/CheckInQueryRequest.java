package com.af.course.api.vo;

import com.af.common.vo.BasePageQuery;
import lombok.Data;

/**
 * @author Tanglinfeng
 * @date 2022/4/22 14:41
 */
@Data
public class CheckInQueryRequest extends BasePageQuery {

    private String studentId;
    private String userId;
    private String name;
    private String checkInName;
    private String courseId;
    private String status;

}
