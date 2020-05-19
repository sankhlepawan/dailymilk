package com.dailyservice.whatsappbot.dto;

import java.util.List;

import com.dailyservice.whatsappbot.model.v1.Order;
import com.dailyservice.whatsappbot.model.v1.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsResponseDTO {

	Order order;
	List<OrderItem> items;
}
