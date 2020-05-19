package com.dailyservice.whatsappbot.repo.v1.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.dailyservice.whatsappbot.dto.SearchQueryRequestDto;
import com.dailyservice.whatsappbot.dto.SearchResponseDTO;
import com.dailyservice.whatsappbot.model.v1.User;
import com.dailyservice.whatsappbot.repo.v1.IUserRepository;

@Repository
@Transactional
public class UserRepositoryImpl extends GenericRepositoryImpl<User> implements IUserRepository {

	@Override
	public SearchResponseDTO<User> search(SearchQueryRequestDto searchQuery) {
		return super.search(searchQuery);
	}

	@Override
	public void delete(Object id) {
		super.delete(id);

	}

	@Override
	public User find(Object o) {
		return super.find(o);
	}

	@Override
	public User update(User t) {
		return super.update(t);
	}

	@Override
	public User save(User t) {
		return super.save(t);
	}

	@Override
	public long count() {
		return super.count();
	}

	@Override
	public List<User> findAll() {
		return super.findAll();
	}

	@Override
	public User findByWhatsappNumber(String whatsappNumber) {
		return (User) getEntityManager().createNamedQuery("findByWhatsappNumber")
		.setParameter("whatsappNumber", whatsappNumber).getSingleResult();
	}

	@Override
	public User enableUser(long userId, boolean enable) {
		int count =  getEntityManager().createNamedQuery("enableUser")
				.setParameter("userId", userId).setParameter("enable",enable)
				.executeUpdate();
		return count == 1 ?  super.find(userId) : null;
	}

}
