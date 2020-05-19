package com.dailyservice.whatsappbot.service.v1;

import com.dailyservice.whatsappbot.dto.SearchQueryRequestDto;
import com.dailyservice.whatsappbot.dto.SearchResponseDTO;
import com.dailyservice.whatsappbot.model.v1.Role;

public interface IRoleService {

	public Role save(Role role);

	public Iterable<Role> findAll();
	
	public SearchResponseDTO<Role> search(SearchQueryRequestDto body);
}

