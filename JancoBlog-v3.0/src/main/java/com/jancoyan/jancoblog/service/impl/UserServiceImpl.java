package com.jancoyan.jancoblog.service.impl;

import com.jancoyan.jancoblog.pojo.User;
import com.jancoyan.jancoblog.mapper.UserMapper;
import com.jancoyan.jancoblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
