package com.hoanghoa.userpurchased.util;

import io.micrometer.core.instrument.util.StringUtils;

import java.util.Date;
import java.util.Objects;

public class StringUtil {

    public static final String PHONE_PATTERN = "^\\\\d{10}$";
    public static final String CHARSET = "UTF-8";
    public static final String USER_PREFIX = "user_";
    public static final String COMMA = ",";
    public static final String UNDERSCORE = "_";
    public static final String EMPTY = "";
    public static final String NULL = null;

    /**
     * Generate trace id, for logging purpose
     * @param params list param to make a trace id
     * @return Trace id
     */
    public static String generateTraceId(String ... params) {
        StringBuilder traceId = new StringBuilder();

        for(String param : params) {
            traceId.append(param)
                    .append(UNDERSCORE);
        }

        traceId.append(new Date().toInstant().toString());

        return traceId.toString();
    }

    /**
     * Convert string value to integer
     * @param value Value to convert
     * @return Integer value or null
     */
    public static Integer convertToInteger(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        return Integer.valueOf(value);
    }

    /**
     * Convert string value to float
     * @param value Value to convert
     * @return Float value or null
     */
    public static Float convertToFloat(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        return Float.valueOf(value);
    }

    public static String valueOf(Object obj) {
        if(Objects.isNull(obj)) {
            return EMPTY;
        }

        return String.valueOf(obj);
    }
}
