package com.jancoyan.jancoblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jancoyan.jancoblog.pojo.UserInfo;
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

    /**
     * 管理员管理的时候获取所有用户
     * @param pn 页码
     * @param limit 容量
     * @param condition 条件
     * @return
     */
    IPage<User> getAll(Integer pn, Integer limit, String condition);

    /**
     * 获取用户汇总信息，用于数据展板
     * @param userId 用户id
     * @return
     */
    VUserTotalData getUserTotalData(String userId);

    /**
     * 获取用户登录信息
     * @param userId 用户id
     * @return
     */
    UserInfo getUserInfo(Integer userId);

    User login(String username, String password);
}
