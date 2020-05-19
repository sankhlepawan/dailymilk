package com.dailyservice.whatsappbot.repo.v1;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dailyservice.whatsappbot.model.v1.Order;

@Repository
public interface IOrderRepo extends CrudRepository<Order, Long> {

	public List<Order> findByWhtsappNumber(String whtsappNumber);
	
}
