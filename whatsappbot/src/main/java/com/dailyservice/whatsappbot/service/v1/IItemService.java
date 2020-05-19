package com.dailyservice.whatsappbot.service.v1;

import com.dailyservice.whatsappbot.dto.ResponseDto;
import com.dailyservice.whatsappbot.dto.SearchQueryRequestDto;
import com.dailyservice.whatsappbot.dto.SearchResponseDTO;
import com.dailyservice.whatsappbot.model.v1.Item;

public interface IItemService {

	public double calculatePriceByQwt(int qwt, String name);
	public Item create(Item item);
	public ResponseDto<Item> findAll();
	SearchResponseDTO<Item> search(SearchQueryRequestDto searchQuery);
	
}
