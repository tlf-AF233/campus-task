package com.af.common.base;

import com.af.common.constant.CommonConstants;
import com.af.common.utils.DateUtil;
import com.af.common.utils.IPGen;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    protected Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date modifyDate;

    protected Integer isDeleted;

    public void initEntity() {
        Date currentDate = new Date();
        this.id = IPGen.generateId();
        this.createDate = currentDate;
        this.modifyDate = currentDate;
        this.isDeleted = CommonConstants.UN_DELETED;
    }

    public boolean isNewRecord() {
        return this.getId() == null;
    }
}
