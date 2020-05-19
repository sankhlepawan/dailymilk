package com.dailyservice.whatsappbot.service.v1;

import com.dailyservice.whatsappbot.model.v1.Category;

public interface ICategoryService {

	public Iterable<Category> findAll();
}
