package com.example.pizza.toppings.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate


@Configuration
class CacheConfig {
    @Value("\${spring.redis.host}")
    lateinit var host: String

    @Value("\${spring.redis.port}")
    lateinit var port: Integer


    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory? {
        val config = RedisStandaloneConfiguration()
        config.setHostName(host)
        config.setPort(port.toInt())
        return JedisConnectionFactory(config)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any>? {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(jedisConnectionFactory()!!)
        return template
    }
}