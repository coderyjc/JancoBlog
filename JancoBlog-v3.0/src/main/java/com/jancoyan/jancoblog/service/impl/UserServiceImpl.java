package com.jancoyan.jancoblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.jancoblog.pojo.Comment;
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

    @Override
    public IPage<User> getAll(Integer pn, Integer limit, String condition) {
        // 分页查询
        IPage<User> iPage = new Page<>(pn, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        System.out.println(condition);

        String[] split = condition.split("--");
        for (String item : split) {
            String[] split2 = item.split("=");
            if(split2.length < 2){
                continue;
            }

            if(split2[0].equals("user_name")){
                wrapper.like("user_name", split2[1]);
            }else if(split2[0].equals("start")){
                wrapper.gt("user_create_date", split2[1]);
            }else if(split2[0].equals("end")){
                wrapper.lt("user_create_date", split2[1]);
            }
        }

        return baseMapper.getAll(iPage, wrapper);
    }
}
