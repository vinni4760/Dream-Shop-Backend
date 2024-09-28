package com.shop.service;

import java.util.List;

import com.shop.entity.Category;

public interface ICategoryService {
	
	Category getCategoryById(Integer id);
	Category getCategoryByName(String name);
	List<Category> getAllCategory();
	Category addCategory(Category category);
	Category updateCategory(Category category,Integer id);
	void deleteCategoryById(Integer id);

}
