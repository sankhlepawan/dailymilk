package com.dailyservice.whatsappbot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.dailyservice.whatsappbot.model.v1.inputs.RedisTemplateInput;
import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
public class RedisConfig {

	@Autowired
	  ObjectMapper mapper;

	  @Autowired
	  RedisConnectionFactory connectionFactory;
	  
	 
	  @Bean
	  RedisTemplate<String, RedisTemplateInput> redisTemplate() {
	    final RedisTemplate<String, RedisTemplateInput> redisTemplate = new RedisTemplate<>();
	    Jackson2JsonRedisSerializer valueSerializer = new Jackson2JsonRedisSerializer(RedisTemplateInput.class);
	    valueSerializer.setObjectMapper(mapper);
	    redisTemplate.setConnectionFactory(connectionFactory);
	    redisTemplate.setKeySerializer(new StringRedisSerializer());
	    redisTemplate.setValueSerializer(valueSerializer);
	    System.out.println("##### redis template  initialized #####");
	    return redisTemplate;
	  }
	  
	  
}