package com.jancoyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.pojo.User;
import com.jancoyan.mapper.UserMapper;
import com.jancoyan.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-06-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public IPage<User> selectAllUserWithoutPassword(Integer page, Integer limit) {
        IPage<User> iPage = new Page<>(page, limit);
        return baseMapper.selectAllUserWithoutPassword(iPage, null);
    }

    @Override
    public Integer getMaxUserId() {
        return baseMapper.getMaxUserId();
    }
}
