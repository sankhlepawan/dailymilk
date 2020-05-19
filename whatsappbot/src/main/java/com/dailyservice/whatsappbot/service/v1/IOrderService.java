package com.dailyservice.whatsappbot.service.v1;

import com.dailyservice.whatsappbot.dto.ResponseDto;
import com.dailyservice.whatsappbot.model.v1.Order;
import com.dailyservice.whatsappbot.model.v1.User;
import com.dailyservice.whatsappbot.model.v1.inputs.RedisTemplateInput;

public interface IOrderService {

	public Order create(User user);

	public String processAndPlaceOrderByWhatsapp(RedisTemplateInput userTemplate);

	public ResponseDto<Order> findAll();

	Order findById(Long orderId);

	public Order update(Order order);
}
