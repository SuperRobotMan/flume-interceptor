package com;

import com.alibaba.fastjson.JSON;

public class JSONUtils {
//    public static void main(String[] args) {
    //验证是否ok
//        String s  = "{\"age\":1}";
//        boolean validate = isValidate(s);
//        System.out.println(validate);
//    }

    public static boolean isValidate(String log) {
        //如果是个json串，就解析成功，如果不是json串，就会抛异常
        try{
            JSON.parse(log);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
