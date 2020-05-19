package com.dailyservice.whatsappbot.repo.v1;

import com.dailyservice.whatsappbot.model.v1.User;

public interface IUserRepository extends IGenericRepository<User>{
	
	public User findByWhatsappNumber(String number);
	
	public User enableUser(long userId, boolean enable);
	
	
}
	