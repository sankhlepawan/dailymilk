package com.dailyservice.whatsappbot.service.v1.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailyservice.whatsappbot.dto.SearchQueryRequestDto;
import com.dailyservice.whatsappbot.dto.SearchResponseDTO;
import com.dailyservice.whatsappbot.model.v1.CollectionStatusType;
import com.dailyservice.whatsappbot.model.v1.MilkCollection;
import com.dailyservice.whatsappbot.repo.v1.IMilkCollectionRepository;
import com.dailyservice.whatsappbot.service.v1.IMilkCollectionService;

@Service
public class MilkCollectionServiceImpl implements IMilkCollectionService {

	
	@Autowired
	IMilkCollectionRepository repo;
	
	
	@Override
	public MilkCollection create(MilkCollection item) {
		item.setStatus(CollectionStatusType.ACCEPTED);
		item.setCreatedOn(new Date());
		return repo.save(item);
	}

	@Override
	public SearchResponseDTO<MilkCollection> search(SearchQueryRequestDto searchQuery) {
		return repo.search(searchQuery);
	}

}
