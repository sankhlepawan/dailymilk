package com.dailyservice.whatsappbot.repo.v1;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dailyservice.whatsappbot.model.v1.SubCategory;

@Repository
public interface ISubCategoryRepository extends CrudRepository<SubCategory, Integer>{

	@Query("select sc from SubCategory sc inner join Category c on c.id = sc.categoryid where c.name=:categoryName")
	public List<SubCategory> findByCategoryName(@Param("categoryName") String name);
	
	public SubCategory findByMenuOrder(Integer input);
}
