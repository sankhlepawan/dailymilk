package com.dailyservice.whatsappbot.repo.v1.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.dailyservice.whatsappbot.dto.SearchQueryRequestDto;
import com.dailyservice.whatsappbot.dto.SearchResponseDTO;
import com.dailyservice.whatsappbot.model.v1.MilkCollection;
import com.dailyservice.whatsappbot.repo.v1.IMilkCollectionRepository;


@Repository
@Transactional
public class MilkCollectionRepositoryImpl extends GenericRepositoryImpl<MilkCollection> implements IMilkCollectionRepository{

	@Override
	public SearchResponseDTO<MilkCollection> search(SearchQueryRequestDto searchQuery) {
		return super.search(searchQuery);
	}

	@Override
	public void delete(Object id) {
		super.delete(id);

	}

	@Override
	public MilkCollection find(Object o) {
		return super.find(o);
	}

	@Override
	public MilkCollection update(MilkCollection t) {
		return super.update(t);
	}

	@Override
	public MilkCollection save(MilkCollection t) {
		return super.save(t);
	}

	@Override
	public long count() {
		return super.count();
	}

	@Override
	public List<MilkCollection> findAll() {
		return super.findAll();
	}

}
