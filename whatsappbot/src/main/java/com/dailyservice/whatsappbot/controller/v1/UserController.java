package com.dailyservice.whatsappbot.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailyservice.whatsappbot.dto.ResponseDto;
import com.dailyservice.whatsappbot.dto.SearchQueryRequestDto;
import com.dailyservice.whatsappbot.dto.SearchResponseDTO;
import com.dailyservice.whatsappbot.model.v1.User;
import com.dailyservice.whatsappbot.service.v1.IUserService;

@RestController
@RequestMapping(value = { "/v1/user" }, consumes = {"text/plain;charset=UTF-8","application/json"})
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	IUserService service;

	@PostMapping(value="/create")
	public User findById(@RequestBody User user){
		return service.save(user);
	}
	
	@GetMapping(value="/findAll")
	public ResponseDto<User> findById(){
		return service.findAll() ;
	}
	
	@PostMapping(value="/enable")
	public ResponseDto<User> enable(@RequestBody User user){
		return service.enable(user) ;
	}
	
	@PostMapping(value="/delete")
	public boolean delete(@RequestBody User user){
		return service.delete(user);
	}
	
	@PostMapping(value="/search")
	public SearchResponseDTO<User> search(@RequestBody SearchQueryRequestDto body){
		return service.search(body);
	}
	
	
}
