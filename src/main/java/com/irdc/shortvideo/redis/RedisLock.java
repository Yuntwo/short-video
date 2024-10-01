package com.irdc.shortvideo.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Package sell
 * Created by yangshu on 2020/5/3 17:33
 * Description：使用redis来加锁和解锁
 * 主要使用redis中的getset与setnx操作
 * 要想可以被 @Autowired 需要注入到容器中，需要使用Component（service中也使用了@Service，@Controller）
 * 使用jpa的时候自动的注入了？？ 使用mybatis的时候需要 @Mapper
 */

@Component
@Slf4j
public class RedisLock {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key
     * @param value  当前的时间 + 超时时间
     * @return
     */
    public boolean lock(String key,String value){
        if (!redisTemplate.opsForValue().setIfAbsent(key,value)) {
            return true;
        }
        String currentValue = (String) redisTemplate.opsForValue().get(key);

        // 如果锁过期，上一个线程中的锁出现了死锁，加锁后没有解锁
        // 业务代码报错，线程没有运行到解锁的操作，为了避免死锁，需要下面的逻辑
        if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()){
            // 获取上一个锁的时间
            String oldValue = (String) redisTemplate.opsForValue().getAndSet(key,value);
            if(!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                return true;
            }
        }
        return false;
    }

    public void unlock(String key,String value){
        // 主要是删除redis中的key
        try {
            String currentValue = (String) redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }
        catch (Exception e){
            log.error("【redis解锁错误】" + e);
        }

    }

}
