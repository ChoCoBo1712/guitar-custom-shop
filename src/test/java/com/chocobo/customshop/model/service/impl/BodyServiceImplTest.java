package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.dao.BodyDao;
import com.chocobo.customshop.model.dao.impl.BodyDaoImpl;
import com.chocobo.customshop.model.service.BodyService;
import com.chocobo.customshop.model.service.criteria.BodyFilterCriteria;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BodyServiceImplTest {

    private BodyDao bodyDao;
    private BodyService bodyService;

    @BeforeAll
    public void init() {
        bodyDao = mock(BodyDao.class);
        MockedStatic<BodyDaoImpl> mockedStatic = mockStatic(BodyDaoImpl.class);
        mockedStatic.when(BodyDaoImpl::getInstance).thenReturn(bodyDao);
        bodyService = BodyServiceImpl.getInstance();
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterNone(int start, int length, String keyword) throws ServiceException, DaoException {
        BodyFilterCriteria criteria = BodyFilterCriteria.NONE;

        bodyService.filter(start, length, criteria, keyword);
        verify(bodyDao, Mockito.atLeastOnce()).selectAll(start, length);
        verify(bodyDao, Mockito.atLeastOnce()).selectCountAll();
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterId(int start, int length, String keyword) throws ServiceException, DaoException {
        BodyFilterCriteria criteria = BodyFilterCriteria.ID;

        bodyService.filter(start, length, criteria, keyword);
        verify(bodyDao, Mockito.atLeastOnce()).selectById(start, length, keyword);
        verify(bodyDao, Mockito.atLeastOnce()).selectCountById(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterName(int start, int length, String keyword) throws ServiceException, DaoException {
        BodyFilterCriteria criteria = BodyFilterCriteria.NAME;

        bodyService.filter(start, length, criteria, keyword);
        verify(bodyDao, Mockito.atLeastOnce()).selectByName(start, length, keyword);
        verify(bodyDao, Mockito.atLeastOnce()).selectCountByName(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterWoodId(int start, int length, String keyword) throws ServiceException, DaoException {
        BodyFilterCriteria criteria = BodyFilterCriteria.WOOD_ID;

        bodyService.filter(start, length, criteria, keyword);
        verify(bodyDao, Mockito.atLeastOnce()).selectByWoodId(start, length, keyword);
        verify(bodyDao, Mockito.atLeastOnce()).selectCountByWoodId(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterNameAndWood(int start, int length, String keyword) throws ServiceException, DaoException {
        BodyFilterCriteria criteria = BodyFilterCriteria.NAME_AND_WOOD;

        bodyService.filter(start, length, criteria, keyword);
        verify(bodyDao, Mockito.atLeastOnce()).selectByNameAndWood(start, length, keyword);
        verify(bodyDao, Mockito.atLeastOnce()).selectCountByNameAndWood(keyword);
    }

    private Stream<Arguments> provideFilterValues() {
        return Stream.of(
                Arguments.of(0, 10, ""),
                Arguments.of(10, 100, "query"),
                Arguments.of(1, 11, "Keyword for search")
        );
    }
}
