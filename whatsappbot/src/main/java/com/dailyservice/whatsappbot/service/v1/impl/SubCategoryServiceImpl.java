package com.dailyservice.whatsappbot.service.v1.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailyservice.whatsappbot.model.v1.SubCategory;
import com.dailyservice.whatsappbot.repo.v1.ISubCategoryRepository;
import com.dailyservice.whatsappbot.service.v1.ISubCategoryService;

@Service
public class SubCategoryServiceImpl implements ISubCategoryService {

	@Autowired
	ISubCategoryRepository repo;

	@Override
	public Iterable<SubCategory> findAll() {
		return repo.findAll();
	}
	
}
