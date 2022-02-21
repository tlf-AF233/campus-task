package com.af.user.api.entity;

import com.af.common.base.BaseEntity;
import com.af.user.api.vo.CollegeVo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * 学院信息
 *
 * @author Tanglinfeng
 * @date 2022/2/6 15:55
 */
@Data
public class College extends BaseEntity<College> {

    private String collegeId;

    private String name;

    public CollegeVo toVo() {
        CollegeVo collegeVo = new CollegeVo();
        BeanUtils.copyProperties(this, collegeVo);
        return collegeVo;
    }
}
