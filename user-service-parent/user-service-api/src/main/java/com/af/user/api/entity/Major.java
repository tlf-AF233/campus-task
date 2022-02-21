package com.af.user.api.entity;

import com.af.common.base.BaseEntity;
import com.af.user.api.vo.MajorVo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * 专业-班级信息
 *
 * @author Tanglinfeng
 * @date 2022/2/6 15:56
 */
@Data
public class Major extends BaseEntity<Major> {

    private String majorId;

    private String majorName;

    private String collegeId;

    // 年级
    private String grade;

    // 班级号
    private Integer classNo;

    public MajorVo toVo() {
        MajorVo majorVo = new MajorVo();
        BeanUtils.copyProperties(this, majorVo);
        return majorVo;
    }
}
