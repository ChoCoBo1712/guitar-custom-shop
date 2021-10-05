package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.dao.GuitarDao;
import com.chocobo.customshop.model.dao.impl.GuitarDaoImpl;
import com.chocobo.customshop.model.service.GuitarService;
import com.chocobo.customshop.model.service.criteria.GuitarFilterCriteria;
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
public class GuitarServiceImplTest {

    private GuitarDao guitarDao;
    private GuitarService guitarService;

    @BeforeAll
    public void init() {
        guitarDao = mock(GuitarDao.class);
        MockedStatic<GuitarDaoImpl> mockedStatic = mockStatic(GuitarDaoImpl.class);
        mockedStatic.when(GuitarDaoImpl::getInstance).thenReturn(guitarDao);
        guitarService = GuitarServiceImpl.getInstance();
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterNone(int start, int length, String keyword) throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.NONE;

        guitarService.filter(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectAll(start, length);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountAll();
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterId(int start, int length, String keyword) throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.ID;

        guitarService.filter(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectById(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountById(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterName(int start, int length, String keyword) throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.NAME;

        guitarService.filter(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectByName(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountByName(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterColor(int start, int length, String keyword) throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.COLOR;

        guitarService.filter(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectByColor(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountByColor(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterBodyId(int start, int length, String keyword) throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.BODY_ID;

        guitarService.filter(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectByBodyId(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountByBodyId(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterNeckId(int start, int length, String keyword) throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.NECK_ID;

        guitarService.filter(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectByNeckId(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountByNeckId(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterPickupId(int start, int length, String keyword) throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.PICKUP_ID;

        guitarService.filter(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectByPickupId(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountByPickupId(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterUserId(int start, int length, String keyword) throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.USER_ID;

        guitarService.filter(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectByUserId(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountByUserId(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterNeckJoint(int start, int length, String keyword) throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.NECK_JOINT;

        guitarService.filter(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectByNeckJoint(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountByNeckJoint(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterOrderStatus(int start, int length, String keyword) throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.ORDER_STATUS;

        guitarService.filter(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectByOrderStatus(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountByOrderStatus(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterForActiveOrderNone(int start, int length, String keyword)
            throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.NONE;

        guitarService.filterForActiveOrder(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectAllForActiveOrder(start, length);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountAllForActiveOrder();
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterForActiveOrderName(int start, int length, String keyword)
            throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.NAME;

        guitarService.filterForActiveOrder(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectByNameForActiveOrder(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountByNameForActiveOrder(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterForActiveOrderColor(int start, int length, String keyword)
            throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.COLOR;

        guitarService.filterForActiveOrder(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectByColorForActiveOrder(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountByColorForActiveOrder(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterForActiveOrderBodyId(int start, int length, String keyword)
            throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.BODY_ID;

        guitarService.filterForActiveOrder(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectByBodyIdForActiveOrder(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountByBodyIdForActiveOrder(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterForActiveOrderNeckId(int start, int length, String keyword)
            throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.NECK_ID;

        guitarService.filterForActiveOrder(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectByNeckIdForActiveOrder(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountByNeckIdForActiveOrder(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterForActiveOrderPickupId(int start, int length, String keyword)
            throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.PICKUP_ID;

        guitarService.filterForActiveOrder(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectByPickupIdForActiveOrder(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountByPickupIdForActiveOrder(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterForActiveOrderUserId(int start, int length, String keyword)
            throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.USER_ID;

        guitarService.filterForActiveOrder(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectByUserIdForActiveOrder(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountByUserIdForActiveOrder(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterForActiveOrderNeckJoint(int start, int length, String keyword)
            throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.NECK_JOINT;

        guitarService.filterForActiveOrder(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectByNeckJointForActiveOrder(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountByNeckJointForActiveOrder(keyword);
    }

    @ParameterizedTest
    @MethodSource("provideFilterValues")
    public void testFilterForActiveOrderOrderStatus(int start, int length, String keyword)
            throws ServiceException, DaoException {
        GuitarFilterCriteria criteria = GuitarFilterCriteria.ORDER_STATUS;

        guitarService.filterForActiveOrder(start, length, criteria, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectByOrderStatus(start, length, keyword);
        verify(guitarDao, Mockito.atLeastOnce()).selectCountByOrderStatus(keyword);
    }

    private Stream<Arguments> provideFilterValues() {
        return Stream.of(
                Arguments.of(0, 10, ""),
                Arguments.of(10, 100, "query"),
                Arguments.of(1, 11, "Keyword for search")
        );
    }
}
