package com.dailyservice.whatsappbot.service.v1;

import com.dailyservice.whatsappbot.dto.SearchQueryRequestDto;
import com.dailyservice.whatsappbot.dto.SearchResponseDTO;

public interface IGenericSearchService<T> {

	SearchResponseDTO<T> search(SearchQueryRequestDto searchQuery);
}
