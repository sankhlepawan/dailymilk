package com.dailyservice.whatsappbot.service.v1.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailyservice.whatsappbot.model.v1.Category;
import com.dailyservice.whatsappbot.repo.v1.ICategoryRepository;
import com.dailyservice.whatsappbot.service.v1.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	ICategoryRepository repo;
	
	@Override
	public Iterable<Category> findAll() {
		return repo.findAll();
	}

}
