package com.rita.product_management.core.common.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Optional;

public final class CustomMapper {

    private static final String DOT = ".";
    private static final String COMMA = ";";
    private static final String EMPTY = "";
    private static final String SPACE = " ";
    private static final String TIME = "T";
    private static final char REPLACE_CHAR = '\u00A0';
    private static final char CHAR_SPACE = ' ';
    private static final int SCALE = 2;
    private static final int FIRST_POSITION = 0;
    private static final int ONE = 1;

    private CustomMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static BigDecimal parseDoubleToBigDecimal(Double value){
        return Optional.ofNullable(value)
                .map(i -> new BigDecimal(Double.toString(value)).setScale(SCALE, RoundingMode.FLOOR))
                .orElse(BigDecimal.ZERO);
    }

    public static BigDecimal parseStringToBigDecimal(String value){
        value = value.substring(value.indexOf(SPACE) + ONE)
                .replace(DOT, EMPTY)
                .replace(COMMA, DOT);
        return parseDoubleToBigDecimal(Double.valueOf(value));
    }

    public static String parseBigDecimalToString(BigDecimal value){
        return NumberFormat.getCurrencyInstance().format(value).replace(REPLACE_CHAR, CHAR_SPACE);
    }

    public static LocalDate parseStringToLocalDate(String value){
        return LocalDate.parse(value.split(TIME)[FIRST_POSITION]);
    }
}
