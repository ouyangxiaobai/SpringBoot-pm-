package com.yuanlrc.base.redis;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisLock {
    private Logger logger=LoggerFactory.getLogger(RedisLock.class);
    @Autowired
    private StringRedisTemplate redisTemplate;


    public boolean lock(String key,String value){
        //setIfAbsent对应redis中的setnx，key存在的话返回false，不存在返回true
        if (redisTemplate.opsForValue().setIfAbsent(key,value)){
            return true;
        }
        //两个问题，Q1超时时间
        String currentValue = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()){
            //Q2 在线程超时的时候，多个线程争抢锁的问题
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                return true;
            }
        }
        return false;
    }

    public void unlock(String key ,String value){
        try{
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)){
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch(Exception e){
            logger.error("redis分布上锁解锁异常, {}",e);
        }

    }

}
