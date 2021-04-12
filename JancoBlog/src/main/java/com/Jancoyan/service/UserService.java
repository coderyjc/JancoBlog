package com.Jancoyan.service;


import com.Jancoyan.dao.UserMapper;
import com.Jancoyan.domain.User;
import com.Jancoyan.domain.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jancoyan
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    /**
     *
     * @param user 用户，其中只有用户名和密码
     * @return
     */
    public User login(User user) {
        boolean success = false;
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(user.getUserName());
        criteria.andUserPwdEqualTo(user.getUserPwd());
        List<User> users = userMapper.selectByExample(example);
        return users.get(0);
    }
}
