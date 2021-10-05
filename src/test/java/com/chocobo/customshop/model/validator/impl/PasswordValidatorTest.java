package com.chocobo.customshop.model.validator.impl;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PasswordValidatorTest {

    @ParameterizedTest
    @MethodSource("providePassword")
    public void testValidate(boolean expected, String password) {
        boolean actual = PasswordValidator.getInstance().validate(password);
        assertEquals(actual, expected);
    }

    private Stream<Arguments> providePassword() {
        return Stream.of(
                Arguments.of(true, "123123Good"),
                Arguments.of(true, "NicePassword1"),
                Arguments.of(true, "faf123SAddd"),
                Arguments.of(false, "Cool password"),
                Arguments.of(false, "<script>alert('hi')</script>"),
                Arguments.of(false, "Short1"),
                Arguments.of(false, "123123123")
        );
    }
}
