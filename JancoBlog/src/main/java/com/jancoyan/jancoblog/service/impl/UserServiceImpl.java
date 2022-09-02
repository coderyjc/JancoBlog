package com.jancoyan.jancoblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.jancoblog.model.domain.User;
import com.jancoyan.jancoblog.mapper.UserMapper;
import com.jancoyan.jancoblog.model.domain.UserInfo;
import com.jancoyan.jancoblog.model.vo.UserTotalDataVO;
import com.jancoyan.jancoblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jancoyan.jancoblog.utils.MD5Util;
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

    @Override
    public IPage<User> getAll(Integer pn, Integer limit, String condition) {
        // 分页查询
        IPage<User> iPage = new Page<>(pn, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        String[] split = condition.split("--");
        for (String item : split) {
            String[] split2 = item.split("=");
            if(split2.length < 2){
                continue;
            }
            if("user_name".equals(split2[0])){
                wrapper.like("user_name", split2[1]);
            }else if("start".equals(split2[0])){
                wrapper.gt("user_create_date", split2[1]);
            }else if("end".equals(split2[0])){
                wrapper.lt("user_create_date", split2[1]);
            }
        }
        return baseMapper.getAll(iPage, wrapper);
    }

    @Override
    public UserTotalDataVO getUserTotalData(String userId) {
        return baseMapper.getUserTotalData(userId);
    }

    @Override
    public UserInfo getUserInfo(Integer userId) {
        return baseMapper.getUserInfo(userId);
    }

    @Override
    public User login(String username, String password) {
        // 登录数据校验
        password = MD5Util.getMD5(password);
        return baseMapper.login(username, password);
    }

}
