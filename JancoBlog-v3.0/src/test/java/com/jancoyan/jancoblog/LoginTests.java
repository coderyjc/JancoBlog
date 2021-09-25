/**
 * @Author: Yan Jingcun
 * @Date: 2021/9/25
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.jancoblog;

import com.jancoyan.jancoblog.utils.JsonWebTokenUtils;
import com.jancoyan.jancoblog.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginTests extends JancoBlogApplicationTests{

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void getToken(){

        String token = JsonWebTokenUtils.createToken(10000L);

        System.out.println("userToken = " + token);

        Long userId = JsonWebTokenUtils.getAppUID(token);

        System.out.println("userName = " + userId);

    }

    @Test
    public void setAndGetToken(){
        redisUtil.set("hello", "1");
        System.out.println(redisUtil.get("hello"));
    }

}
