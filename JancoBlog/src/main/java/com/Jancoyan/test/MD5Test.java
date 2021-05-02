package com.Jancoyan.test;

import com.Jancoyan.utils.MD5Util;
import org.junit.Test;

public class MD5Test {

    /**
     * 查看md5加密后的密码
     */
    @Test
    public void testMD5(){
        String password = "333";
        System.out.println(MD5Util.getMD5(password));
        System.out.println(MD5Util.getMD5("333"));
    }

}
