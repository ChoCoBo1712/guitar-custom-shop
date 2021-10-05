package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.dao.WoodDao;
import com.chocobo.customshop.model.dao.impl.WoodDaoImpl;
import com.chocobo.customshop.model.service.WoodService;
import com.chocobo.customshop.model.service.criteria.WoodFilterCriteria;
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
public class WoodServiceImplTest {

    private WoodDao woodDao;
    private WoodService woodService;

    @BeforeAll
    public void init() {
        woodDao = mock(WoodDao.class);
        MockedStatic<WoodDaoImpl> mockedStatic = mockStatic(WoodDaoImpl.class);
        mockedStatic.when(WoodDaoImpl::getInstance).thenReturn(woodDao);
        woodService = WoodServiceImpl.getInstance();
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterNone(int start, int length, String keyword) throws ServiceException, DaoException {
        WoodFilterCriteria criteria = WoodFilterCriteria.NONE;

        woodService.filter(start, length, criteria, keyword);
        verify(woodDao, Mockito.atLeastOnce()).selectAll(start, length);
        verify(woodDao, Mockito.atLeastOnce()).selectCountAll();
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterId(int start, int length, String keyword) throws ServiceException, DaoException {
        WoodFilterCriteria criteria = WoodFilterCriteria.ID;

        woodService.filter(start, length, criteria, keyword);
        verify(woodDao, Mockito.atLeastOnce()).selectById(start, length, keyword);
        verify(woodDao, Mockito.atLeastOnce()).selectCountById(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterName(int start, int length, String keyword) throws ServiceException, DaoException {
        WoodFilterCriteria criteria = WoodFilterCriteria.NAME;

        woodService.filter(start, length, criteria, keyword);
        verify(woodDao, Mockito.atLeastOnce()).selectByName(start, length, keyword);
        verify(woodDao, Mockito.atLeastOnce()).selectCountByName(keyword);
    }

    private Stream<Arguments> provideFilterValues() {
        return Stream.of(
                Arguments.of(0, 10, ""),
                Arguments.of(10, 100, "query"),
                Arguments.of(1, 11, "Keyword for search")
        );
    }
}
