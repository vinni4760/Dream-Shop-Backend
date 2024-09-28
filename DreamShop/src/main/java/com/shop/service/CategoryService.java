package com.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.entity.Category;
import com.shop.exceptions.AlreadyExistException;
import com.shop.exceptions.ResourceNotFoundException;
import com.shop.repository.ICategoryRepository;

@Service
public class CategoryService implements ICategoryService {
	
	@Autowired
	private ICategoryRepository categoryRepository;

	@Override
	public Category getCategoryById(Integer id) {
		return categoryRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Category Not Found"));
	}

	@Override
	public Category getCategoryByName(String name) {
		return categoryRepository.findByName(name);

	}

	@Override
	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	@Override
	public Category addCategory(Category category) {
		return Optional.of(category)
		.filter(c->!categoryRepository.existsByName(category.getName()))
		.map(categoryRepository::save)
		.orElseThrow(()->new AlreadyExistException("! Oops\s"+category.getName()+"\sCategory Already Exists !"));
		
	}

	@Override
	public Category updateCategory(Category category, Integer id) {
		
		return  categoryRepository.findById(id)
		.map(existingcategory->updateExistingCategory(existingcategory,category))
		.map(categoryRepository::save)
		.orElseThrow(()-> { throw  new ResourceNotFoundException("Category Not Found");});

	}
	
	public Category updateExistingCategory(Category existingcategory,Category category) {
		
//		category.setName(existingcategory.getName());		
		existingcategory.setName(category.getName());
         return existingcategory;
	}

	@Override
	public void deleteCategoryById(Integer id) {
		
		categoryRepository.findById(id)
		.ifPresentOrElse(categoryRepository::delete,
				()->{throw new ResourceNotFoundException("Category Not Found");});
		
	}

}
