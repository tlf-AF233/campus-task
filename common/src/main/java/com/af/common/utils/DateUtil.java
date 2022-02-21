package com.af.common.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author Tanglinfeng
 * @date 2022/2/8 17:25
 */
public class DateUtil {

    /**
     * LocalDate转Date
     * @param localDate
     * @return
     */
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
