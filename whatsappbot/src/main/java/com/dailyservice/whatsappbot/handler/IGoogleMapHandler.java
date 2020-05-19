package com.dailyservice.whatsappbot.handler;

import com.dailyservice.whatsappbot.model.v1.inputs.RedisTemplateInput;

public interface IGoogleMapHandler {

	void setUerAddressByLatLong(Float lat, Float lng, RedisTemplateInput user);
}
