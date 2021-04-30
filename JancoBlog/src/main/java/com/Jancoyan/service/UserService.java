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
     * 更新不为空的字端
     * @param user 用户
     * @return 更新完成的用户
     */
    public User updateUserSelective(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        return user;
    }

    /**
     * 登录
     * @param user 用户，其中只有用户名和密码
     * @return 登录成功返回用户对象存到session中
     */
    public User login(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(user.getUserName());
        criteria.andUserPwdEqualTo(user.getUserPwd());
        List<User> users = userMapper.selectByExample(example);
        return users.get(0);
    }
}
