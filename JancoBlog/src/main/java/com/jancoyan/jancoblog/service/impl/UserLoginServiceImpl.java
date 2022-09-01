package com.jancoyan.jancoblog.service.impl;

import com.jancoyan.jancoblog.pojo.UserLogin;
import com.jancoyan.jancoblog.mapper.UserLoginMapper;
import com.jancoyan.jancoblog.service.UserLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-10-15
 */
@Service
public class UserLoginServiceImpl extends ServiceImpl<UserLoginMapper, UserLogin> implements UserLoginService {

}
