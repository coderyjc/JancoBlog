package com.Jancoyan.Dao;


import com.Jancoyan.Domain.User;

/**
 * @author Jancoyan
 */
public interface IUserDao {

    /**
     *  用户登录Dao
     * @param user 姓名和密码
     * @return 登录是否成功
     */
    int login(User user);

}
