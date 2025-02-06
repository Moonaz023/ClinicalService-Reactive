package com.hsms.clinicalService.redisconfig;

import com.hsms.clinicalService.entity.Hospital;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.*;

@Configuration
@ComponentScan("com.hsms.clinicalService")
@EnableRedisRepositories(basePackages = "com.hsms.clinicalService")
public class RedisConfig {
    @Bean
    public ReactiveRedisOperations<String, Hospital> playerRedisOperations(LettuceConnectionFactory connectionFactory) {
        RedisSerializationContext<String, Hospital> serializationContext = RedisSerializationContext
                .<String, Hospital>newSerializationContext(new StringRedisSerializer())
                .key(new StringRedisSerializer())
                .value(new GenericToStringSerializer<>(Hospital.class))
                .hashKey(new Jackson2JsonRedisSerializer<>(String.class))
                .hashValue(new GenericJackson2JsonRedisSerializer())
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }


}
