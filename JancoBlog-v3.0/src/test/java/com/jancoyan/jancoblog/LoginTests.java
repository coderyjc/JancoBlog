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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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


    @Test
    public void queryIp(){
        String ip = "101.201.64.102";
        String url = "https://ip.taobao.com//outGetIpInfo?ip=" + ip + "&accessKey=alibaba-inc";

        try {
            URL realUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            //请求成功
            if (connection.getResponseCode() == 200) {
                System.out.println(connection.getResponseMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
