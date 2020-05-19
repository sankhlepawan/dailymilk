package com.dailyservice.whatsappbot.service.v1;

import com.dailyservice.whatsappbot.model.v1.SubCategory;

public interface ISubCategoryService {

	public Iterable<SubCategory> findAll();
}
