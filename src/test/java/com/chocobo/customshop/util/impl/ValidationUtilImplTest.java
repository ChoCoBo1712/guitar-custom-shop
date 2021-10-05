package com.chocobo.customshop.util.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.util.ValidationUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import java.util.stream.Stream;

import static com.chocobo.customshop.web.command.RequestAttribute.DUPLICATE_EMAIL_ERROR;
import static com.chocobo.customshop.web.command.RequestAttribute.DUPLICATE_LOGIN_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValidationUtilImplTest {
    private static final String EMAIL = "mail@mail.com";
    private static final String LOGIN = "login";
    private static final String REDIRECT_URL = "url";
    private static final String AMP = "&";
    private static final String IS_TRUE = "=true";

    private ValidationUtil validationUtil;
    UserService userService;

    @BeforeAll
    public void init() throws ServiceException {
        userService = mock(UserService.class);
        MockedStatic<UserServiceImpl> mockedStatic = mockStatic(UserServiceImpl.class);
        mockedStatic.when(UserServiceImpl::getInstance).thenReturn(userService);
        validationUtil = ValidationUtilImpl.getInstance();

        when(userService.isLoginUnique(any())).thenReturn(false);
        when(userService.isLoginUnique(LOGIN)).thenReturn(true);

        when(userService.isEmailUnique(any())).thenReturn(false);
        when(userService.isEmailUnique(EMAIL)).thenReturn(true);
    }

    @ParameterizedTest
    @MethodSource("isUserDuplicateTestData")
    void testCheckingDuplicate(String email, String login, boolean expectedResult, String expectedRedirect) throws ServiceException {
        Pair<Boolean, String> result = validationUtil.isUserDuplicate(email, login, REDIRECT_URL);
        assertEquals(expectedResult, result.getLeft());
        assertEquals(expectedRedirect, result.getRight());
    }

    public static Stream<Arguments> isUserDuplicateTestData() {
        return Stream.of(
                Arguments.of(EMAIL, LOGIN, false, REDIRECT_URL),
                Arguments.of(EMAIL + 1, LOGIN, true,
                        REDIRECT_URL + AMP + DUPLICATE_EMAIL_ERROR + IS_TRUE),
                Arguments.of(EMAIL, LOGIN + 1, true,
                        REDIRECT_URL + AMP + DUPLICATE_LOGIN_ERROR + IS_TRUE),
                Arguments.of(EMAIL + 1, LOGIN + 1, true,
                        REDIRECT_URL + AMP + DUPLICATE_EMAIL_ERROR + IS_TRUE + AMP + DUPLICATE_LOGIN_ERROR + IS_TRUE)
        );
    }

    @ParameterizedTest
    @MethodSource("updatedUserDuplicateTestData")
    void testCheckingDuplicatesForUpdate(String email, String login, boolean emailsMatch,
                                         boolean loginsMatch, boolean expectedResult, String expectedRedirect) throws ServiceException {
        Pair<Boolean, String> result =
                validationUtil.isUpdatedUserDuplicate(email, login, REDIRECT_URL, emailsMatch, loginsMatch);
        assertEquals(expectedResult, result.getLeft());
        assertEquals(expectedRedirect, result.getRight());
    }

    public static Stream<Arguments> updatedUserDuplicateTestData() {
        return Stream.of(
                Arguments.of(EMAIL, LOGIN, true, true, false, REDIRECT_URL),
                Arguments.of(EMAIL, LOGIN, false, true, false, REDIRECT_URL),
                Arguments.of(EMAIL + 1, LOGIN, false, true, true,
                        REDIRECT_URL + AMP + DUPLICATE_EMAIL_ERROR + IS_TRUE),
                Arguments.of(EMAIL, LOGIN, true, false, false, REDIRECT_URL),
                Arguments.of(EMAIL, LOGIN + 1, true, false, true,
                        REDIRECT_URL + AMP + DUPLICATE_LOGIN_ERROR + IS_TRUE),
                Arguments.of(EMAIL, LOGIN, false, false, false, REDIRECT_URL),
                Arguments.of(EMAIL + 1, LOGIN, false, false, true,
                        REDIRECT_URL + AMP + DUPLICATE_EMAIL_ERROR + IS_TRUE),
                Arguments.of(EMAIL, LOGIN + 1, false, false, true,
                        REDIRECT_URL + AMP + DUPLICATE_LOGIN_ERROR + IS_TRUE),
                Arguments.of(EMAIL + 1, LOGIN + 1, false, false, true,
                        REDIRECT_URL + AMP + DUPLICATE_EMAIL_ERROR + IS_TRUE + AMP + DUPLICATE_LOGIN_ERROR + IS_TRUE)
        );
    }
}