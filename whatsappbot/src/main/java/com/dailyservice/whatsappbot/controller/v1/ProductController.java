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
import com.dailyservice.whatsappbot.model.v1.Item;
import com.dailyservice.whatsappbot.service.v1.IItemService;

@RestController
@RequestMapping(value = { "/v1/product" })
@CrossOrigin("*")
public class ProductController {
	
  @Autowired
  IItemService service;
  
  	@PostMapping(value="/create")
	public Item create(@RequestBody Item item){
		return service.create(item);
	}
  	

  	@GetMapping(value="/findAll")
	public ResponseDto<Item> findAll(){
		return service.findAll();
	}
  	
  	@PostMapping(value="/search")
	public SearchResponseDTO<Item> search(@RequestBody SearchQueryRequestDto body){
		return service.search(body);
	}
  	
  	
		
	
}
