package com.dailyservice.whatsappbot.service.v1;

import com.dailyservice.whatsappbot.dto.ResponseDto;
import com.dailyservice.whatsappbot.dto.SearchQueryRequestDto;
import com.dailyservice.whatsappbot.dto.SearchResponseDTO;
import com.dailyservice.whatsappbot.model.v1.User;

public interface IUserService{
  
	public User save(User u);
	
	public User findByWhatsappNumber(String whtsappNumber);

	public ResponseDto<User> findAll();

	public SearchResponseDTO<User> search(SearchQueryRequestDto body);

	public boolean delete(User user);

	public ResponseDto<User> enable(User user);
}
