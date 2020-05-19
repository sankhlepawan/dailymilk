package com.dailyservice.whatsappbot.handler.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.dailyservice.whatsappbot.handler.IRedisHandler;
import com.dailyservice.whatsappbot.model.v1.inputs.RedisTemplateInput;


@Component
public class RedisHandlerImpl implements IRedisHandler {

	
	@Autowired
	private RedisTemplate<String, RedisTemplateInput> redisTemplate;

	 @Value("${redis.ttl}")
	 private long ttl;
	 
//	 @Autowired
//	 private CacheManager cacheManager;
//	  
	  
	  
	@Override
	public RedisTemplateInput get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void set(String key, RedisTemplateInput template) {
		redisTemplate.opsForValue().set(key, template, ttl, TimeUnit.SECONDS);
		
		

	}

	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}
	
//	@EventListener
//	  public void onApplicationEvent(ApplicationReadyEvent event) {
//	      cacheManager.getCacheNames()
//	                  .parallelStream()
//	                  .forEach(n -> cacheManager.getCache(n).clear());
//	  }

}
