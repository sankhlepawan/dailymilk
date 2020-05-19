package com.dailyservice.whatsappbot.service.v1.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailyservice.whatsappbot.dto.SearchQueryRequestDto;
import com.dailyservice.whatsappbot.dto.SearchResponseDTO;
import com.dailyservice.whatsappbot.model.v1.Role;
import com.dailyservice.whatsappbot.model.v1.User;
import com.dailyservice.whatsappbot.repo.v1.IRoleRepository;
import com.dailyservice.whatsappbot.service.v1.IRoleService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class RoleServiceImpl implements IRoleService {

	@Autowired
	IRoleRepository repo;
	
	@Override
	public Role save(Role role) {
		log.info("inside @roleService @save method entry..");
		return repo.save(role);
	}

	@Override
	public Iterable<Role> findAll() {
		return repo.findAll();
	}

	@Override
	public SearchResponseDTO<Role> search(SearchQueryRequestDto body) {
		return repo.search(body);
	}

	
}
