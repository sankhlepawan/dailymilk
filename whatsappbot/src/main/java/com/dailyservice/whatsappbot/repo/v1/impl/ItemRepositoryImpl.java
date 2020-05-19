package com.dailyservice.whatsappbot.repo.v1.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.dailyservice.whatsappbot.dto.SearchQueryRequestDto;
import com.dailyservice.whatsappbot.dto.SearchResponseDTO;
import com.dailyservice.whatsappbot.model.v1.Item;
import com.dailyservice.whatsappbot.repo.v1.IItemRepository;

@Repository
@Transactional
public class ItemRepositoryImpl  extends GenericRepositoryImpl<Item> implements IItemRepository{

	@Override
	public SearchResponseDTO<Item> search(SearchQueryRequestDto searchQuery) {
		return super.search(searchQuery);
	}

	@Override
	public void delete(Object id) {
		super.delete(id);

	}

	@Override
	public Item find(Object o) {
		return super.find(o);
	}

	@Override
	public Item update(Item t) {
		return super.update(t);
	}

	@Override
	public Item save(Item t) {
		return super.save(t);
	}

	@Override
	public long count() {
		return super.count();
	}

	@Override
	public List<Item> findAll() {
		return super.findAll();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findDistinctItemByAvailable(boolean available) {
		return getEntityManager().createNamedQuery("findDistinctItemByAvailable")
				.setParameter("available", available).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Item> findBySubCategoriesAndAvailableAndQwt(String subCategoryName, boolean available, int qwt) {
		return getEntityManager().createNamedQuery("findBySubCategoriesAndAvailableAndQwt")
				.setParameter("subCategoryName", subCategoryName)
				.setParameter("available", available)
				.setParameter("qwt", qwt)
				.getResultList();
	}

}
