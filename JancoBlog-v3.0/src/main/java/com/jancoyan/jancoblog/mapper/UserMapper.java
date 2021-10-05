package com.jancoyan.jancoblog.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jancoyan.jancoblog.pojo.UserInfo;
import com.jancoyan.jancoblog.pojo.VUserTotalData;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<User> getAll(IPage<User> iPage, Wrapper ew);

    VUserTotalData getUserTotalData(String userId);

    UserInfo getUserInfo(Integer userId);
}
