package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.entity.Category;
import com.shop.exceptions.ResourceNotFoundException;
import com.shop.response.ApiResponse;
import com.shop.service.ICategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	 @Autowired
	 private ICategoryService categoryService;
	 
	 @GetMapping("/all")
	 public ResponseEntity<ApiResponse> getAllCategoriesApi(){
		List<Category> categories =  categoryService.getAllCategory();
		 return new ResponseEntity<ApiResponse>(new ApiResponse("Found", categories),
				 HttpStatus.OK);
	 }
	 
	 @GetMapping("/getby/{id}")
	 public ResponseEntity<ApiResponse> getCategoryByIdApi(@PathVariable Integer id){
		 return new ResponseEntity<ApiResponse>(new ApiResponse("Category !", categoryService.getCategoryById(id)),
				 HttpStatus.OK);
	 }
	 
	 @GetMapping("/getby/{name}")
	 public ResponseEntity<ApiResponse> getCategoryByNameApi(@PathVariable String name){
		 return new ResponseEntity<ApiResponse>(new ApiResponse("Category !", categoryService.getCategoryByName(name)),
				 HttpStatus.OK);
	 }
	
	 @PostMapping("/add")
	 public ResponseEntity<ApiResponse> addCategoryApi(@RequestBody Category category){
		 try {
			 categoryService.addCategory(category);
			 return new ResponseEntity<ApiResponse>(new ApiResponse("Added Success !", category.getName()),
					 HttpStatus.OK);
			
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(e.getMessage(), null),
					HttpStatus.CONFLICT);
		}
	 }
	 
	 
	 @PutMapping("/update/{id}")
	 public ResponseEntity<ApiResponse> updateCategoryApi(@PathVariable Integer id,@RequestBody Category category){
		 try {
			categoryService.updateCategory(category, id);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Update Success !", null),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(e.getMessage(), null),
					HttpStatus.NOT_FOUND);
		}
	 }
	 
	 @DeleteMapping("/delete/{id}")
	 public ResponseEntity<ApiResponse> deleteCategoryApi(@PathVariable Integer id){
		 try {
			categoryService.deleteCategoryById(id);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Delete Success !", null),
					HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(e.getMessage(), null),
					HttpStatus.NOT_FOUND);
		}
		 
	 }
	 
	 
	 
	 
}
