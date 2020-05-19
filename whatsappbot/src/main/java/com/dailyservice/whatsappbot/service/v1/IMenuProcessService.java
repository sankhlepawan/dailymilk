package com.dailyservice.whatsappbot.service.v1;

import com.dailyservice.whatsappbot.model.v1.WhatsappRequestBody;
import com.dailyservice.whatsappbot.model.v1.inputs.RedisTemplateInput;


public interface IMenuProcessService {

	public String processMainMenuRequest(RedisTemplateInput userTemplate,  WhatsappRequestBody body);

	public String processPlaceOrderRequest(RedisTemplateInput userTemplate, WhatsappRequestBody body);

	public String processOrderConfirmQuantityRequest(RedisTemplateInput userTemplate, WhatsappRequestBody body); 
	
	public String processSelectAddressRequest(RedisTemplateInput userTemplate, WhatsappRequestBody body);

	public String processAddressConfirmedRequest(RedisTemplateInput userTemplate, WhatsappRequestBody body);

	public String processItemQunatityRequest(RedisTemplateInput userTemplate, WhatsappRequestBody body);

	public String processPlaceOrderMonthlyRequestMenu(RedisTemplateInput userTemplate, WhatsappRequestBody body);

	public String processOrderOneTimeRequest(RedisTemplateInput userTemplate, WhatsappRequestBody body);

}
