package com.af.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
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
@Slf4j
public class DateUtil {

    public static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";


    public static String formatDateString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATTER);
        return simpleDateFormat.format(date);
    }

    public static Date strToDate(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATTER);
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            LogUtil.error(log, "时间序列化异常, {}", e);
        }
        return null;
    }
}
