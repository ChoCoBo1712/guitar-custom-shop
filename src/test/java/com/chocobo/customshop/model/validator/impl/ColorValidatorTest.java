package com.chocobo.customshop.model.validator.impl;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ColorValidatorTest {

    @ParameterizedTest
    @MethodSource("provideColor")
    public void testValidate(boolean expected, String color) {
        boolean actual = ColorValidator.getInstance().validate(color);
        assertEquals(actual, expected);
    }

    private Stream<Arguments> provideColor() {
        return Stream.of(
                Arguments.of(true, "Burst"),
                Arguments.of(true, "Lemon-Burst 59"),
                Arguments.of(false, "Lemon!"),
                Arguments.of(false, "Very, very good color"),
                Arguments.of(false, "Beautiful Beautiful Beautiful color")
        );
    }
}
