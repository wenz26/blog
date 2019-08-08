package com.cwz.blog.blogback.common.cache;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/*
* RedisManager
*/
@SuppressWarnings("unchecked")
public class RedisManager {

    /**
     * Redis常见的五大数据类型
     *  String（字符串）、List（列表）、Set（集合）、Hash（散列）、ZSet（有序集合）
     *  stringRedisTemplate.opsForValue()[String（字符串）]
     *  stringRedisTemplate.opsForList()[List（列表）]
     *  stringRedisTemplate.opsForSet()[Set（集合）]
     *  stringRedisTemplate.opsForHash()[Hash（散列）]
     *  stringRedisTemplate.opsForZSet()[ZSet（有序集合）]
     */

    /*
     * 默认过期时长，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 30 * 1;

    /*
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1;

    private RedisTemplate redisTemplate;

    public void set(String key, Object value, long expire){
        try {
            if(expire == NOT_EXPIRE){
                redisTemplate.opsForValue().set(key, value);
            } else {
                redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }

    /*
    * <T> T表示返回值是一个泛型，传递啥，就返回啥类型的数据，而单独的T就是表示限制你传递的参数类型
    * 这个<T> T 表示的是返回值T是泛型，T是一个占位符，用来告诉编译器，这个东西先给我留着，等我编译的时候，告诉你。
    */

    /*
     * 这个<T> T 可以传入任何类型的List
     * 参数T
     *     第一个 表示是泛型
     *     第二个 表示返回的是T类型的数据
     *     第三个 限制参数类型为T
     *
     *   private <T> T getListFisrt(List<T> data) {
     *       if (data == null || data.size() == 0) {
     *           return null;
     *       }
     *       return data.get(0);
     *   }
     */

    public <T> T get(String key, Class<T> clazz){
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }

    public RedisTemplate<String, Object> getRedisTemplate(){
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

}
