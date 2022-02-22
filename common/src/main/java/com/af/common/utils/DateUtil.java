package com.af.common.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author Tanglinfeng
 * @date 2022/2/8 17:25
 */
public class DateUtil {

    public static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";


    public static String formatDateString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATTER);
        return simpleDateFormat.format(date);
    }
}
