package com.dailyservice.whatsappbot.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailyservice.whatsappbot.dto.SearchQueryRequestDto;
import com.dailyservice.whatsappbot.dto.SearchResponseDTO;
import com.dailyservice.whatsappbot.model.v1.MilkCollection;
import com.dailyservice.whatsappbot.service.v1.IMilkCollectionService;

@RestController
@RequestMapping(value = { "/v1/collection" })
@CrossOrigin("*")
public class MilkCollectionController {

		@Autowired
		IMilkCollectionService service;
	  
	  	@PostMapping(value="/create")
		public MilkCollection create(@RequestBody MilkCollection item){
			return service.create(item);
		}
	  	

	  	@PostMapping(value="/search")
		public SearchResponseDTO<MilkCollection> search(@RequestBody SearchQueryRequestDto body){
			return service.search(body);
		}
}
