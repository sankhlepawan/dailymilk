package com.dailyservice.whatsappbot.repo.v1;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dailyservice.whatsappbot.model.v1.Item;

@Repository
public interface IItemRepository extends IGenericRepository<Item>{

	List<String> findDistinctItemByAvailable(boolean available);

	List<Item> findBySubCategoriesAndAvailableAndQwt(String subCategoryName, boolean available, int qwt);

}
