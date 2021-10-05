package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.dao.PickupDao;
import com.chocobo.customshop.model.dao.impl.PickupDaoImpl;
import com.chocobo.customshop.model.service.PickupService;
import com.chocobo.customshop.model.service.criteria.PickupFilterCriteria;
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
public class PickupServiceImplTest {

    private PickupDao pickupDao;
    private PickupService pickupService;

    @BeforeAll
    public void init() {
        pickupDao = mock(PickupDao.class);
        MockedStatic<PickupDaoImpl> mockedStatic = mockStatic(PickupDaoImpl.class);
        mockedStatic.when(PickupDaoImpl::getInstance).thenReturn(pickupDao);
        pickupService = PickupServiceImpl.getInstance();
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterNone(int start, int length, String keyword) throws ServiceException, DaoException {
        PickupFilterCriteria criteria = PickupFilterCriteria.NONE;

        pickupService.filter(start, length, criteria, keyword);
        verify(pickupDao, Mockito.atLeastOnce()).selectAll(start, length);
        verify(pickupDao, Mockito.atLeastOnce()).selectCountAll();
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterId(int start, int length, String keyword) throws ServiceException, DaoException {
        PickupFilterCriteria criteria = PickupFilterCriteria.ID;

        pickupService.filter(start, length, criteria, keyword);
        verify(pickupDao, Mockito.atLeastOnce()).selectById(start, length, keyword);
        verify(pickupDao, Mockito.atLeastOnce()).selectCountById(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterName(int start, int length, String keyword) throws ServiceException, DaoException {
        PickupFilterCriteria criteria = PickupFilterCriteria.NAME;

        pickupService.filter(start, length, criteria, keyword);
        verify(pickupDao, Mockito.atLeastOnce()).selectByName(start, length, keyword);
        verify(pickupDao, Mockito.atLeastOnce()).selectCountByName(keyword);
    }

    private Stream<Arguments> provideFilterValues() {
        return Stream.of(
                Arguments.of(0, 10, ""),
                Arguments.of(10, 100, "query"),
                Arguments.of(1, 11, "Keyword for search")
        );
    }
}
