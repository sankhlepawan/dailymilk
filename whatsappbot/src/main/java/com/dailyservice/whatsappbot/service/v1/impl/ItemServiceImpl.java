package com.dailyservice.whatsappbot.service.v1.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailyservice.whatsappbot.dto.ResponseDto;
import com.dailyservice.whatsappbot.dto.SearchQueryRequestDto;
import com.dailyservice.whatsappbot.dto.SearchResponseDTO;
import com.dailyservice.whatsappbot.model.v1.Item;
import com.dailyservice.whatsappbot.repo.v1.IItemRepository;
import com.dailyservice.whatsappbot.service.v1.IItemService;
import com.dailyservice.whatsappbot.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ItemServiceImpl implements IItemService{

	@Autowired
	IItemRepository repo;
	
	@Override
	public double calculatePriceByQwt(int qwt, String name) {
		List<Item> item = repo.findBySubCategoriesAndAvailableAndQwt(name, true, 0);
		if(qwt > 0) {
			return Utils.calculateItemTotal(qwt, item.get(0).getPrice());
		}
		return 0;
	}

	@Override
	public Item create(Item item) {
		return repo.save(item);
	}

	@Override
	public ResponseDto<Item> findAll() {
		return new ResponseDto<Item>(repo.findAll(), repo.count());
	}
	
	@Override
	public SearchResponseDTO<Item> search(SearchQueryRequestDto searchQuery) {
		return repo.search(searchQuery);
	}

}
