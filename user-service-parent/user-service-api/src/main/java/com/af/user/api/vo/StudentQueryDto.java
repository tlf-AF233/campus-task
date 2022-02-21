package com.af.user.api.vo;

import com.af.common.vo.BasePageQuery;
import lombok.Data;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/2/18 23:54
 */
@Data
public class StudentQueryDto extends BasePageQuery {

    private String studentId;
    private List<String> collegeIdList;
    private List<Integer> majorIdList;

}
