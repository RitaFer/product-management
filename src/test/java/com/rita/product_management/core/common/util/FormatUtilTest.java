package com.rita.product_management.core.common.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FormatUtilTest {

    @Test
    void givenPrivateConstructor_whenInstantiated_thenThrowsUnsupportedOperationException() throws NoSuchMethodException {
        Constructor<FormatUtil> constructor = FormatUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThrows(InvocationTargetException.class, constructor::newInstance);

        try {
            constructor.newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            assertInstanceOf(UnsupportedOperationException.class, e.getCause());
            assertEquals("Utility class", e.getCause().getMessage());
        }
    }

    @Test
    void givenValidBigDecimal_whenCurrencyFormat_thenFormattedString() {
        BigDecimal value = BigDecimal.valueOf(1234.56);
        String result = FormatUtil.currencyFormat(value);
        assertEquals("1.234,56", result);
    }

    @Test
    void givenNullBigDecimal_whenCurrencyFormat_thenReturnEmptyString() {
        BigDecimal value = null;
        String result = FormatUtil.currencyFormat(value);
        assertEquals("", result);
    }

    @Test
    void givenValidDouble_whenPercentFormat_thenFormattedString() {
        Double value = 1.25;
        String result = FormatUtil.percentFormat(value);
        assertEquals("25", result);
    }

    @Test
    void givenNullDouble_whenPercentFormat_thenReturnEmptyString() {
        Double value = null;
        String result = FormatUtil.percentFormat(value);
        assertEquals("", result);
    }

    @Test
    void givenNullBigDecimal_whenCurrencyFormat_thenHandleGracefully() {
        BigDecimal value = null;
        String result = FormatUtil.currencyFormat(value);
        assertEquals("", result);
    }

    @Test
    void givenInvalidInput_whenPercentFormat_thenHandleException() {
        Double value = Double.POSITIVE_INFINITY;
        String result = FormatUtil.percentFormat(value);
        assertEquals("", result);
    }

}
