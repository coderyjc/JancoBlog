/**
 * @Author: Yan Jingcun
 * @Date: 2021/9/21
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.jancoblog.utils;

import com.jancoyan.jancoblog.pojo.User;

public class UserUtils {

    public static String getToken(User user){
        return String.valueOf(user.hashCode());
    }




}
