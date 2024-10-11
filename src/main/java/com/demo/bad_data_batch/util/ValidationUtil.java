package com.demo.bad_data_batch.util;

import org.apache.commons.lang3.StringUtils;

public class ValidationUtil {
    static public boolean isNullOrBlank(final String text) {
        return StringUtils.isBlank(text) || "NULL".equals(text);
    }
}
