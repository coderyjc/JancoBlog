package com.jancoyan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
public interface UserService extends IService<User> {

    IPage<User> selectAllUserWithoutPassword(Integer page, Integer limit);

    Integer getMaxUserId();

}
