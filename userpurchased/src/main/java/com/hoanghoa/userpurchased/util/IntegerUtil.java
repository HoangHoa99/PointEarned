package com.hoanghoa.userpurchased.util;

import io.micrometer.core.instrument.util.StringUtils;

public class IntegerUtil {
    public static Integer valueOf(String str) {
        if(StringUtils.isEmpty(str)) {
            return 0;
        }

        return Integer.valueOf(str);
    }
}