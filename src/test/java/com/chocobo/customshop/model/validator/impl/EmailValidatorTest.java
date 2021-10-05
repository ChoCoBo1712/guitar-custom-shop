package com.chocobo.customshop.model.validator.impl;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmailValidatorTest {

    @ParameterizedTest
    @MethodSource("provideEmail")
    public void testValidate(boolean expected, String email) {
        boolean actual = EmailValidator.getInstance().validate(email);
        assertEquals(actual, expected);
    }

    private Stream<Arguments> provideEmail() {
        return Stream.of(
                Arguments.of(true, "user@gmail.com"),
                Arguments.of(true, "address123@mail.ru"),
                Arguments.of(false, "user@"),
                Arguments.of(false, "user"),
                Arguments.of(false, "user@a")
        );
    }
}
