package com.jancoyan.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jancoyan
 * @since 2021-06-28
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<User> selectAllUserWithoutPassword(IPage<User> iPage, Object o);

    Integer getMaxUserId();
}
