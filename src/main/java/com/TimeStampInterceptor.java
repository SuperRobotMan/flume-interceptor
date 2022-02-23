package com;

import com.alibaba.fastjson.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class TimeStampInterceptor implements Interceptor {
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        //将这条日志拦截下来
        //取出key timeStamp
        //取出body 里面的ts（日志的生成时间）
        //将ts的值赋值给header里面的key

        //获取header头
        Map<String, String> headers = event.getHeaders();

        //获取body中的ts
        byte[] body = event.getBody();
        String log = new String(body, StandardCharsets.UTF_8);
        //将string转换成json串
        JSONObject jsonObject = JSONObject.parseObject(log);
        //通过json串获取到ts的值
        String ts = jsonObject.getString("ts");

        //将ts的值赋值给headers
        headers.put("timestamp",ts);
        //最后将event返回
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> list) {
        //将list遍历一遍，然后将每个event执行一下单event的操作
        for (Event event : list) {
            intercept(event);
        }
        //最后将list返回
        return list;
    }

    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new TimeStampInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
