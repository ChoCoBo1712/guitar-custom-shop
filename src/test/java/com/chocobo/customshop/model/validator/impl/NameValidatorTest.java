package com.chocobo.customshop.model.validator.impl;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NameValidatorTest {

    @ParameterizedTest
    @MethodSource("provideName")
    public void testValidate(boolean expected, String name) {
        boolean actual = NameValidator.getInstance().validate(name);
        assertEquals(actual, expected);
    }

    private Stream<Arguments> provideName() {
        return Stream.of(
                Arguments.of(true, "P-90"),
                Arguments.of(true, "Les Paul Custom"),
                Arguments.of(true, "Stratocaster 63"),
                Arguments.of(false, "Cool* guitar"),
                Arguments.of(false, "<script>alert('hi')</script>"),
                Arguments.of(false, "Looooooooooooooooooooooong name")
        );
    }
}
