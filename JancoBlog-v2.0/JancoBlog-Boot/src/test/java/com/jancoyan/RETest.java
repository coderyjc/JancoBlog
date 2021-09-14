/**
 * @Author: Yan Jingcun
 * @Date: 2021/7/3
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan;

import org.junit.jupiter.api.Test;

public class RETest {

    @Test
    public void testRe(){
        String str = "name=";
        String[] split = str.split("=");
        System.out.println(split[0]);
        System.out.println(split[1]);
    }

}
