package com.rita.product_management.core.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class FormatUtil {

    private static final BigDecimal PURE_VALUE_IN_DECIMAL_FORMAT = BigDecimal.valueOf(1);
    private static final BigDecimal PERCENT = BigDecimal.valueOf(100);
    private static final String BR_CURRENCY_FORMAT_WITHOUT_RS = "#,##0.00";
    private static final DecimalFormatSymbols BR_DECIMAL_SYMBOL = DecimalFormatSymbols.getInstance(Locale.of("pt", "BR"));

    private FormatUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String currencyFormat(BigDecimal value){
        final DecimalFormat decimalFormat =
                new DecimalFormat(BR_CURRENCY_FORMAT_WITHOUT_RS, BR_DECIMAL_SYMBOL);
        return decimalFormat.format(value);
    }


    public static String percentFormat(Double value){
        var result =
                (BigDecimal.valueOf(value)
                        .subtract(PURE_VALUE_IN_DECIMAL_FORMAT)
                        .multiply(PERCENT)
                        .longValue());
        return String.valueOf(result);
    }

}
