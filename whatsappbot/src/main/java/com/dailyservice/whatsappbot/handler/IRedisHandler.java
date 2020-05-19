package com.dailyservice.whatsappbot.handler;

import com.dailyservice.whatsappbot.model.v1.inputs.RedisTemplateInput;

public interface IRedisHandler {
   
	public RedisTemplateInput get(String key);
	public void set(String key, RedisTemplateInput template);
	public void delete(String key);
}
