package com.Jancoyan.Service.Impl;

import com.Jancoyan.Dao.IUserDao;
import com.Jancoyan.Domain.User;
import com.Jancoyan.Service.IUserService;
import com.Jancoyan.Utils.SqlSessionUtil;

public class UserServiceImpl implements IUserService {

    private IUserDao userDao = SqlSessionUtil.getSqlSession().getMapper(IUserDao.class);

    @Override
    public boolean login(User user) {
        boolean success;
        success = userDao.login(user) == 1;
        return success;
    }
}
