package com.Jancoyan.Service;

import com.Jancoyan.Domain.User;

public interface IUserService {

    /**
     * 用户登录
     * @param user 用户实体，只有姓名和密码
     * @return 登录状态，成功或失败
     */
    boolean login(User user);

}
