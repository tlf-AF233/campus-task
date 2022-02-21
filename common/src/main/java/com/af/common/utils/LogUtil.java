package com.af.common.utils;

import org.slf4j.Logger;

/**
 * @author Tanglinfeng
 * @date 2022/2/5 16:16
 */
public class LogUtil {

    public static void debug(final Logger logger, final String msg, final Object... values) {
        if (logger.isDebugEnabled()) {
            logger.debug(msg, values);
        }
    }

    public static void info(final Logger logger, final String msg, final Object... values) {
        if (logger.isInfoEnabled()) {
            logger.info(msg, values);
        }
    }

    public static void error(final Logger logger, final String msg, final Object... values) {
        if (logger.isErrorEnabled()) {
            logger.error(msg, values);
        }
    }

    public static void warn(final Logger logger, final String msg, final Object... values) {
        if (logger.isWarnEnabled()) {
            logger.warn(msg, values);
        }
    }
}
