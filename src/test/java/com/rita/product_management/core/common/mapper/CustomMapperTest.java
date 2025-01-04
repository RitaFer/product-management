package com.rita.product_management.core.common.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;

class CustomMapperTest {

    @Test
    void givenPrivateConstructor_whenInstantiated_thenThrowsUnsupportedOperationException() throws NoSuchMethodException {
        Constructor<CustomMapper> constructor = CustomMapper.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThrows(InvocationTargetException.class, constructor::newInstance, "Expected InvocationTargetException because of private constructor");

        try {
            constructor.newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            assertInstanceOf(UnsupportedOperationException.class, e.getCause());
            assertEquals("Utility class", e.getCause().getMessage());
        }
    }

    @Test
    void givenValidDouble_whenParseDoubleToBigDecimal_thenReturnBigDecimal() {
        Double value = 1234.567;
        BigDecimal result = CustomMapper.parseDoubleToBigDecimal(value);
        assertEquals(new BigDecimal("1234.56"), result);
    }

    @Test
    void givenNullDouble_whenParseDoubleToBigDecimal_thenReturnBigDecimalZero() {
        Double value = null;
        BigDecimal result = CustomMapper.parseDoubleToBigDecimal(value);
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void givenValidStringWithCurrencyPrefix_whenParseStringToBigDecimal_thenReturnBigDecimal() {
        String value = "R$ 1.234,56";
        BigDecimal result = CustomMapper.parseStringToBigDecimal(value);
        assertEquals(new BigDecimal("1234.56"), result);
    }

    @Test
    void givenValidStringWithoutCurrencyPrefix_whenParseStringToBigDecimal_thenReturnBigDecimal() {
        String value = "1.234,56";
        BigDecimal result = CustomMapper.parseStringToBigDecimal(value);
        assertEquals(new BigDecimal("1234.56"), result);
    }

    @Test
    void givenInvalidString_whenParseStringToBigDecimal_thenReturnBigDecimalZero() {
        String value = "INVALID";
        BigDecimal result = CustomMapper.parseStringToBigDecimal(value);
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void givenValidBigDecimal_whenParseBigDecimalToString_thenReturnFormattedString() {
        BigDecimal value = new BigDecimal("1234.56");
        String result = CustomMapper.parseBigDecimalToString(value);
        assertEquals("R$ 1.234,56", result);
    }

    @Test
    void givenNullBigDecimal_whenParseBigDecimalToString_thenReturnEmptyString() {
        BigDecimal value = null;
        String result = CustomMapper.parseBigDecimalToString(value);
        assertEquals("", result);
    }

    @Test
    void givenValidDateString_whenParseStringToLocalDate_thenReturnLocalDate() {
        String value = "2025-01-03T15:30:45";
        LocalDate result = CustomMapper.parseStringToLocalDate(value);
        assertEquals(LocalDate.of(2025, 1, 3), result);
    }

    @Test
    void givenInvalidDateString_whenParseStringToLocalDate_thenReturnNull() {
        String value = "INVALID";
        LocalDate result = CustomMapper.parseStringToLocalDate(value);
        assertNull(result);
    }

    @Test
    void givenNullString_whenParseStringToLocalDate_thenReturnNull() {
        String value = null;
        LocalDate result = CustomMapper.parseStringToLocalDate(value);
        assertNull(result);
    }

}
