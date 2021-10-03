package com.chocobo.customshop.web.command.impl.ajax;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.criteria.UserFilterCriteria;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public class GetUsersCommand extends AbstractAjaxCommand<User> {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    Pair<Long, List<User>> filterForDatatables(int start, int length, String filterCriteria, String searchValue) throws ServiceException {
        return StringUtils.isEmpty(searchValue)
                ? userService.filter(start, length, UserFilterCriteria.NONE, null)
                : userService.filter(start, length, UserFilterCriteria.valueOf(filterCriteria), searchValue);
    }

    @Override
    Pair<Long, List<User>> filterForSelect(int start, int pageSize, String filterCriteria, String searchValue) throws ServiceException {
        return userService.filter(start, pageSize, UserFilterCriteria.valueOf(filterCriteria), searchValue);
    }

    @Override
    Optional<User> getFromServiceById(long entityId) throws ServiceException {
        return userService.findById(entityId);
    }
}
