package dev.sandrocaseiro.template.utils;

import org.springframework.util.StringUtils;

public final class NumberUtil {
    private NumberUtil() {}

    public static int toInt(String value, int defaultValue) {
        if (StringUtils.isEmpty(value))
            return defaultValue;

        return Integer.parseInt(value);
    }
}
