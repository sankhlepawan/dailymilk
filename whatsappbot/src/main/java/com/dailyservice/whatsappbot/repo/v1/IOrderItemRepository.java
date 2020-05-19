package com.dailyservice.whatsappbot.repo.v1;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dailyservice.whatsappbot.model.v1.OrderItem;

public interface IOrderItemRepository extends CrudRepository<OrderItem, Long> {

	
	List<OrderItem> findByOrderId(Long orderId);
	
}
