package com.chocobo.customshop.model.validator.impl;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginValidatorTest {

    @ParameterizedTest
    @MethodSource("provideLogin")
    public void testValidate(boolean expected, String login) {
        boolean actual = LoginValidator.getInstance().validate(login);
        assertEquals(actual, expected);
    }

    private Stream<Arguments> provideLogin() {
        return Stream.of(
                Arguments.of(true, "login1"),
                Arguments.of(true, "AlPhA123"),
                Arguments.of(false, "user@"),
                Arguments.of(false, "short"),
                Arguments.of(false, "user@gmail.com")
        );
    }
}
