package com.dailyservice.whatsappbot.repo.v1;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dailyservice.whatsappbot.model.v1.Category;

@Repository
public interface ICategoryRepository  extends CrudRepository<Category, Long>{

	@Query("select sc from Category sc Order By sc.menuOrder asc")
	public List<Category> findAllOrderByMenuOrderAsc();
	
	public Category findByMenuOrder(Integer menuOrder);
}
