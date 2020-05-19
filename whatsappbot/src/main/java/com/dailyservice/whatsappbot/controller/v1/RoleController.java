package com.dailyservice.whatsappbot.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailyservice.whatsappbot.dto.ResponseDto;
import com.dailyservice.whatsappbot.dto.SearchQueryRequestDto;
import com.dailyservice.whatsappbot.dto.SearchResponseDTO;
import com.dailyservice.whatsappbot.model.v1.Role;
import com.dailyservice.whatsappbot.service.v1.IRoleService;

@RestController
@RequestMapping(value = { "/v1/role" })
@CrossOrigin("*")
public class RoleController {

	@Autowired
	IRoleService service;
	
	@GetMapping("/findAll")
	public ResponseDto<Role> findAll() {
		return new ResponseDto<Role>(service.findAll(),200);
	}
	
	@PostMapping("/save")
	public Role save(@RequestBody Role role) {
		return service.save(role);
	}
	
	@PostMapping("/search")
	public SearchResponseDTO<Role> search(@RequestBody SearchQueryRequestDto query) {
		return service.search(query);
	}
	
}
