package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.dao.NeckDao;
import com.chocobo.customshop.model.dao.impl.NeckDaoImpl;
import com.chocobo.customshop.model.service.NeckService;
import com.chocobo.customshop.model.service.criteria.NeckFilterCriteria;
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
public class NeckServiceImplTest {

    private NeckDao neckDao;
    private NeckService neckService;

    @BeforeAll
    public void init() {
        neckDao = mock(NeckDao.class);
        MockedStatic<NeckDaoImpl> mockedStatic = mockStatic(NeckDaoImpl.class);
        mockedStatic.when(NeckDaoImpl::getInstance).thenReturn(neckDao);
        neckService = NeckServiceImpl.getInstance();
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterNone(int start, int length, String keyword) throws ServiceException, DaoException {
        NeckFilterCriteria criteria = NeckFilterCriteria.NONE;

        neckService.filter(start, length, criteria, keyword);
        verify(neckDao, Mockito.atLeastOnce()).selectAll(start, length);
        verify(neckDao, Mockito.atLeastOnce()).selectCountAll();
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterId(int start, int length, String keyword) throws ServiceException, DaoException {
        NeckFilterCriteria criteria = NeckFilterCriteria.ID;

        neckService.filter(start, length, criteria, keyword);
        verify(neckDao, Mockito.atLeastOnce()).selectById(start, length, keyword);
        verify(neckDao, Mockito.atLeastOnce()).selectCountById(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterName(int start, int length, String keyword) throws ServiceException, DaoException {
        NeckFilterCriteria criteria = NeckFilterCriteria.NAME;

        neckService.filter(start, length, criteria, keyword);
        verify(neckDao, Mockito.atLeastOnce()).selectByName(start, length, keyword);
        verify(neckDao, Mockito.atLeastOnce()).selectCountByName(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterWoodId(int start, int length, String keyword) throws ServiceException, DaoException {
        NeckFilterCriteria criteria = NeckFilterCriteria.WOOD_ID;

        neckService.filter(start, length, criteria, keyword);
        verify(neckDao, Mockito.atLeastOnce()).selectByWoodId(start, length, keyword);
        verify(neckDao, Mockito.atLeastOnce()).selectCountByWoodId(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterFretboardWoodId(int start, int length, String keyword) throws ServiceException, DaoException {
        NeckFilterCriteria criteria = NeckFilterCriteria.FRETBOARD_WOOD_ID;

        neckService.filter(start, length, criteria, keyword);
        verify(neckDao, Mockito.atLeastOnce()).selectByFretboardWoodId(start, length, keyword);
        verify(neckDao, Mockito.atLeastOnce()).selectCountByFretboardWoodId(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterNameAndWood(int start, int length, String keyword) throws ServiceException, DaoException {
        NeckFilterCriteria criteria = NeckFilterCriteria.NAME_AND_WOOD;

        neckService.filter(start, length, criteria, keyword);
        verify(neckDao, Mockito.atLeastOnce()).selectByNameAndWood(start, length, keyword);
        verify(neckDao, Mockito.atLeastOnce()).selectCountByNameAndWood(keyword);
    }

    private Stream<Arguments> provideFilterValues() {
        return Stream.of(
                Arguments.of(0, 10, ""),
                Arguments.of(10, 100, "query"),
                Arguments.of(1, 11, "Keyword for search")
        );
    }
}
