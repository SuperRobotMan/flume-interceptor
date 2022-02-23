package com;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

public class ETLInterceptor implements Interceptor {

    @Override
    public void initialize() {

    }



    /**
     * 处理的是单event 就是处理的每一条数据
     * @param event
     * @return
     */
    @Override
    public Event intercept(Event event) {
        //取数据，然后对数据进行处理

        //获取body值
        byte[] body = event.getBody();

        String log = new String(body, StandardCharsets.UTF_8);

        //将日志取出来以后判断他是不是json串，是否符合我们所需要的格式
        if (JSONUtils.isValidate(log)){
            //如果符合要求，直接将event返回
            return event;
        }

        //如果验证不符合要求，就返回个null
        return null;
    }


    /**
     * 如果来了一批数据的时候，处理一批数据里面的每一个数据
     * @param list
     * @return
     */
    @Override
    public List<Event> intercept(List<Event> list) {
        //目标是将每个event取出来看下是否为空，因为上面已经判断了单个的event，是空就给删掉
        Iterator<Event> iterator = list.iterator();
        while (iterator.hasNext()){
            if (iterator.next() == null){
                //如果等于空，就给他删除掉
                iterator.remove();
            }
        }

        return list;
    }


    @Override
    public void close() {

    }

    //写一个静态内部类，来创建一个对象
    public static class Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new ETLInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }

}
