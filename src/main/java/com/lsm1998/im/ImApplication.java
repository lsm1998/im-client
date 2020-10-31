package com.lsm1998.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImApplication
{
    public static void main(String[] args)
    {
        // VM option: -Djava.awt.headless=false
        SpringApplication.run(ImApplication.class, args);
    }

    /**
     * 设置数据库索引
     */
//    @PostConstruct
//    public void select()
//    {
//        LettuceConnectionFactory jedisConnectionFactory = (LettuceConnectionFactory) stringRedisTemplate.getConnectionFactory();
//        jedisConnectionFactory.setDatabase(0);
//        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
//        jedisConnectionFactory.afterPropertiesSet();
//        jedisConnectionFactory.resetConnection();
//
//        stringRedisTemplate.opsForValue().set("key", "value");
//        RedisConnection connection = jedisConnectionFactory.getConnection();
//        System.out.println(new String(connection.get("key".getBytes())));
//        connection.select(10);
//        System.out.println(new String(connection.get("key".getBytes())));
//    }
}
