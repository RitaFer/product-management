package com.rita.product_management.core.common.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    public static BigDecimal parseDoubleToBigDecimal(Double value) {
        log.debug("Parsing Double value [{}] to BigDecimal with scale [{}]", value, SCALE);
        return Optional.ofNullable(value)
                .map(i -> new BigDecimal(Double.toString(value)).setScale(SCALE, RoundingMode.FLOOR))
                .orElseGet(() -> {
                    log.info("Received null value for Double. Returning BigDecimal.ZERO.");
                    return BigDecimal.ZERO;
                });
    }

    public static BigDecimal parseStringToBigDecimal(String value) {
        log.debug("Parsing String value [{}] to BigDecimal", value);
        try {
            value = value.substring(value.indexOf(SPACE) + ONE)
                    .replace(DOT, EMPTY)
                    .replace(COMMA, DOT);
            log.debug("Formatted String value to parse: [{}]", value);
            return parseDoubleToBigDecimal(Double.valueOf(value));
        } catch (Exception e) {
            log.error("Error parsing String [{}] to BigDecimal. Returning BigDecimal.ZERO.", value, e);
            return BigDecimal.ZERO;
        }
    }

    public static String parseBigDecimalToString(BigDecimal value) {
        log.debug("Parsing BigDecimal value [{}] to String", value);
        try {
            String result = NumberFormat.getCurrencyInstance().format(value).replace(REPLACE_CHAR, CHAR_SPACE);
            log.debug("Formatted BigDecimal to String: [{}]", result);
            return result;
        } catch (Exception e) {
            log.error("Error parsing BigDecimal [{}] to String. Returning empty String.", value, e);
            return EMPTY;
        }
    }

    public static LocalDate parseStringToLocalDate(String value) {
        log.debug("Parsing String value [{}] to LocalDate", value);
        try {
            LocalDate date = LocalDate.parse(value.split(TIME)[FIRST_POSITION]);
            log.debug("Parsed LocalDate: [{}]", date);
            return date;
        } catch (Exception e) {
            log.error("Error parsing String [{}] to LocalDate. Returning null.", value, e);
            return null;
        }
    }

}
