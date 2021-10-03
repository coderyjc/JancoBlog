package com.jancoyan.jancoblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jancoyan.jancoblog.pojo.VUserTotalData;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
public interface UserService extends IService<User> {
    IPage<User> getAll(Integer pn, Integer limit, String condition);

    VUserTotalData getUserTotalData(String userId);
}
