package com.rita.product_management.core.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class FormatUtil {

    private static final BigDecimal PURE_VALUE_IN_DECIMAL_FORMAT = BigDecimal.valueOf(1);
    private static final BigDecimal PERCENT = BigDecimal.valueOf(100);
    private static final String BR_CURRENCY_FORMAT_WITHOUT_RS = "#,##0.00";
    private static final DecimalFormatSymbols BR_DECIMAL_SYMBOL = DecimalFormatSymbols.getInstance(Locale.forLanguageTag("pt-BR"));

    private FormatUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String currencyFormat(BigDecimal value) {
        log.debug("Formatting BigDecimal value [{}] as Brazilian currency", value);

        try {
            final DecimalFormat decimalFormat = new DecimalFormat(BR_CURRENCY_FORMAT_WITHOUT_RS, BR_DECIMAL_SYMBOL);
            String formattedValue = decimalFormat.format(value);
            log.debug("Formatted currency value: [{}]", formattedValue);
            return formattedValue;
        } catch (Exception e) {
            log.error("Error formatting BigDecimal value [{}] as currency. Returning empty string.", value, e);
            return "";
        }
    }

    public static String percentFormat(Double value) {
        log.debug("Formatting Double value [{}] as percentage", value);

        try {
            BigDecimal decimalValue = BigDecimal.valueOf(value);
            BigDecimal result = decimalValue.subtract(PURE_VALUE_IN_DECIMAL_FORMAT).multiply(PERCENT);
            long longValue = result.longValue();
            log.debug("Formatted percentage value: [{}]", longValue);
            return String.valueOf(longValue);
        } catch (Exception e) {
            log.error("Error formatting Double value [{}] as percentage. Returning empty string.", value, e);
            return "";
        }
    }

}

