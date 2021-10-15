/**
 * @Author: Yan Jingcun
 * @Date: 2021/10/15
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.jancoblog.utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class NetworkUtils {

    /**
     * 查找ip归属地
     * @param ip ip地址
     * @return
     */
    public static String queryAddressByIp(String ip) {
        String address = null;
        try {
            address = getAddress(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }


    /**
     * 获取地址
     * @param ip
     * @return
     * @throws Exception
     */
    private static String getAddress(String ip) throws Exception{

        String path = "https://ip.taobao.com//outGetIpInfo?ip=" + ip + "&accessKey=alibaba-inc";
        String returnStr = getRs(path);
        JSONObject json;

        if(returnStr != null){
            json = new JSONObject(returnStr);
            if("0".equals(json.get("code").toString())){
                StringBuffer buffer = new StringBuffer();
                //国家
                buffer.append(decodeUnicode(json.optJSONObject("data").getString("country")));
                //省份
                buffer.append(decodeUnicode(json.optJSONObject("data").getString("region")));
                //市区
                buffer.append(decodeUnicode(json.optJSONObject("data").getString("city")));
                return buffer.toString();
            }else{
                return "获取地址失败";
            }
        }
        return null;
    }

    /**
     * 从url获取结果
     * @param path
     * @param params
     * @param encoding
     * @return
     */
    private static String getRs(String path){

        URL url = null;
        HttpURLConnection connection = null;

        try {

            url = new URL(path);
            // 新建连接实例
            connection = (HttpURLConnection)url.openConnection();

            // 设置连接超时时间，单位毫秒
            connection.setConnectTimeout(2000);
            // 设置读取数据超时时间，单位毫秒
            connection.setReadTimeout(2000);
            // 是否打开输出流 true|false
            connection.setDoInput(true);
            // 是否打开输入流true|false
            connection.setDoOutput(true);
            // 提交方法POST|GET
            connection.setRequestMethod("POST");
            // 是否缓存true|false
            connection.setUseCaches(false);
            // 打开连接端口
            connection.connect();

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());

            out.writeBytes(path);
            out.flush();
            out.close();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder buffer = new StringBuilder();
            String line = "";

            while ((line = reader.readLine())!= null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            connection.disconnect();// 关闭连接
        }
        return null;
    }


    /**
     * 对字符串进行解码
     * @param theString
     * @return
     */
    private static String decodeUnicode(String theString){
        char aChar;
        int len = theString.length();
        StringBuffer buffer = new StringBuffer(len);
        for (int i = 0; i < len;) {
            aChar = theString.charAt(i++);
            if(aChar == '\\'){
                aChar = theString.charAt(i++);
                if(aChar == 'u'){
                    int val = 0;
                    for(int j = 0; j < 4; j++){
                        aChar = theString.charAt(i++);
                        switch (aChar) {
                            case '0':case '1':case '2':case '3':
                            case '4':case '5':case '6':case '7':
                            case '8':case '9':
                                val = (val << 4) + aChar - '0';
                                break;
                            case 'a':case 'b':case 'c':case 'd':
                            case 'e':case 'f':
                                val = (val << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':case 'B':case 'C':case 'D':
                            case 'E':case 'F':
                                val = (val << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed      encoding.");
                        }
                    }
                    buffer.append((char) val);
                }else{
                    if(aChar == 't'){ aChar = '\t';}
                    if(aChar == 'r'){ aChar = '\r';}
                    if(aChar == 'n'){ aChar = '\n';}
                    if(aChar == 'f'){ aChar = '\f';}
                    buffer.append(aChar);
                }
            }else{
                buffer.append(aChar);
            }
        }
        return buffer.toString();
    }

}
