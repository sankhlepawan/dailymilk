package com.dailyservice.whatsappbot.service.v1;

import com.dailyservice.whatsappbot.dto.SearchQueryRequestDto;
import com.dailyservice.whatsappbot.dto.SearchResponseDTO;
import com.dailyservice.whatsappbot.model.v1.MilkCollection;

public interface IMilkCollectionService {

	public MilkCollection create(MilkCollection item);
	SearchResponseDTO<MilkCollection> search(SearchQueryRequestDto searchQuery);
}
