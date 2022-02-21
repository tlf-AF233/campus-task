package com.af.common.base;

import com.af.common.constant.CommonConstants;
import com.af.common.utils.DateUtil;
import com.af.common.utils.IPGen;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author Tanglinfeng
 * @date 2022/2/5 15:31
 */
@Data
public class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;

    protected Date createDate;

    protected Date modifyDate;

    protected Integer isDeleted;

    public void initEntity() {
        Date currentDate = DateUtil.asDate(LocalDate.now());
        this.id = IPGen.generateId();
        this.createDate = currentDate;
        this.modifyDate = currentDate;
        this.isDeleted = CommonConstants.UN_DELETED;
    }
}
