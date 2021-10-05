package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.dao.UserDao;
import com.chocobo.customshop.model.dao.impl.UserDaoImpl;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.criteria.UserFilterCriteria;
import com.chocobo.customshop.util.HashingUtil;
import com.chocobo.customshop.util.impl.HashingUtilImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {

    public static final String PASSWORD = "string";
    public static final byte[] PASSWORD_HASH = PASSWORD.getBytes(StandardCharsets.UTF_8);

    private UserDao userDao;
    private HashingUtil hashingUtil;
    private UserService userService;

    @BeforeAll
    public void init() throws ServiceException {
        userDao = mock(UserDao.class);
        MockedStatic<UserDaoImpl> mockedUserDao = mockStatic(UserDaoImpl.class);
        mockedUserDao.when(UserDaoImpl::getInstance).thenReturn(userDao);

        hashingUtil = mock(HashingUtil.class);
        MockedStatic<HashingUtilImpl> mockedHashingUtil = mockStatic(HashingUtilImpl.class);
        mockedHashingUtil.when(HashingUtilImpl::getInstance).thenReturn(hashingUtil);

        when(hashingUtil.generateHash(eq(PASSWORD), any())).thenReturn(PASSWORD_HASH);

        userService = UserServiceImpl.getInstance();
    }

    @Test
    public void testLoginSuccess() throws DaoException, ServiceException {
        User user = createUser();
        when(userDao.selectByLogin(eq(user.getLogin()))).thenReturn(Optional.of(user));

        Optional<User> result = userService.login(user.getLogin(), PASSWORD);

        assertTrue(result.isPresent(), "Method should return user if login is successful");
        assertEquals(user, result.get());
    }

    @Test
    public void testLoginFailureNoUser() throws DaoException, ServiceException {
        User user = createUser();
        when(userDao.selectByLogin(any())).thenReturn(Optional.empty());

        Optional<User> result = userService.login(user.getLogin(), PASSWORD);

        assertFalse(result.isPresent(), "Method should return empty optional if no user found");
    }

    @Test
    public void testLoginFailureWrongPassword() throws DaoException, ServiceException {
        User user = createUser();
        when(userDao.selectByLogin(eq(user.getLogin()))).thenReturn(Optional.of(user));

        Optional<User> result = userService.login(user.getLogin(), "Glibberish");

        assertFalse(result.isPresent(), "Method should return empty optional if password is incorrect");
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterNone(int start, int length, String keyword) throws ServiceException, DaoException {
        UserFilterCriteria criteria = UserFilterCriteria.NONE;

        userService.filter(start, length, criteria, keyword);
        verify(userDao, Mockito.atLeastOnce()).selectAll(start, length);
        verify(userDao, Mockito.atLeastOnce()).selectCountAll();
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterId(int start, int length, String keyword) throws ServiceException, DaoException {
        UserFilterCriteria criteria = UserFilterCriteria.ID;

        userService.filter(start, length, criteria, keyword);
        verify(userDao, Mockito.atLeastOnce()).selectById(start, length, keyword);
        verify(userDao, Mockito.atLeastOnce()).selectCountById(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterEmail(int start, int length, String keyword) throws ServiceException, DaoException {
        UserFilterCriteria criteria = UserFilterCriteria.EMAIL;

        userService.filter(start, length, criteria, keyword);
        verify(userDao, Mockito.atLeastOnce()).selectByEmail(start, length, keyword);
        verify(userDao, Mockito.atLeastOnce()).selectCountByEmail(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterLogin(int start, int length, String keyword) throws ServiceException, DaoException {
        UserFilterCriteria criteria = UserFilterCriteria.LOGIN;

        userService.filter(start, length, criteria, keyword);
        verify(userDao, Mockito.atLeastOnce()).selectByLogin(start, length, keyword);
        verify(userDao, Mockito.atLeastOnce()).selectCountByLogin(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterRole(int start, int length, String keyword) throws ServiceException, DaoException {
        UserFilterCriteria criteria = UserFilterCriteria.ROLE;

        userService.filter(start, length, criteria, keyword);
        verify(userDao, Mockito.atLeastOnce()).selectByRole(start, length, keyword);
        verify(userDao, Mockito.atLeastOnce()).selectCountByRole(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterStatus(int start, int length, String keyword) throws ServiceException, DaoException {
        UserFilterCriteria criteria = UserFilterCriteria.STATUS;

        userService.filter(start, length, criteria, keyword);
        verify(userDao, Mockito.atLeastOnce()).selectByStatus(start, length, keyword);
        verify(userDao, Mockito.atLeastOnce()).selectCountByStatus(keyword);
    }

    private Stream<Arguments> provideFilterValues() {
        return Stream.of(
                Arguments.of(0, 10, ""),
                Arguments.of(10, 100, "query"),
                Arguments.of(1, 11, "Keyword for search")
        );
    }

    private User createUser(){
        return User.builder()
                .setLogin("login")
                .setPasswordHash(PASSWORD_HASH)
                .build();
    }
}
