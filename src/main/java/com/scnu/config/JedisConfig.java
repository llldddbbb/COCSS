package com.scnu.config;

import com.scnu.dao.cache.RedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * Created by ldb on 2017/6/3.
 */
@Configuration
public class JedisConfig {

    @Value("${REDIS_HOST}")
    private String REDIS_HOST;

    @Value("${REDIS_PORT}")
    private Integer REDIS_PORT;

    @Bean
    public JedisPool getJedisPool(){
        JedisPool jedisPool=new JedisPool(REDIS_HOST,REDIS_PORT);
        return jedisPool;
    }

    @Bean
    public RedisDao getRedisDao(){
        RedisDao redisDao=new RedisDao();
        return redisDao;
    }

}
