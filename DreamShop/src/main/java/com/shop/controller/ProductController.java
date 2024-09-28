package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.Productdto;
import com.shop.entity.Product;
import com.shop.exceptions.ResourceNotFoundException;
import com.shop.request.ProductRequest;
import com.shop.response.ApiResponse;
import com.shop.service.IProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	 @Autowired
	 private IProductService productService;
	 
	 @GetMapping("/all")
	 public ResponseEntity<ApiResponse> getAllProductsApi(){
		 
		List<Product> products = productService.getAllProducts();
	     List<Productdto> productdtos =	productService.getConvertedProducts(products);
		 return new ResponseEntity<ApiResponse>(new ApiResponse("Products !", productdtos),
				 HttpStatus.OK);
	 }
	 
	 @GetMapping("/by/id/{id}")
	 public ResponseEntity<ApiResponse> getProductByIdApi(@PathVariable Integer id){
		 try {
		     Product product =	productService.getProductByid(id);
		 Productdto productdto =     productService.convertodto(product);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Success !",productdto ),
					HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Failed !", e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
	 }
	 
	 @GetMapping("/by/name/{name}")
	 public ResponseEntity<ApiResponse> getProductByNameApi(@PathVariable String  name){
		 try {
			 List<Product> products = productService.getProductsByName(name);
			   
			   if (products.isEmpty()) {
				   return new ResponseEntity<ApiResponse>(new ApiResponse("No Products Available !", null),
						   HttpStatus.NOT_FOUND);
	            }
			 
			 List<Productdto> productdtos = productService.getConvertedProducts(products);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Success !",productdtos ),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Failed !", e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
	 }
	 
	 @GetMapping("/by/category/{category}")
	 public ResponseEntity<ApiResponse> getAllProductsByCategoryApi(@PathVariable String category){
		try {
			 List<Product> products = productService.getProductsByCategory(category);
			  if (products.isEmpty()) {
				   return new ResponseEntity<ApiResponse>(new ApiResponse("No Products Available !", null),
						   HttpStatus.NOT_FOUND);
	            }
			 List<Productdto> productdtos = productService.getConvertedProducts(products);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Success !",productdtos ),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Failed !", e.getMessage()),
					HttpStatus.CONFLICT);
		}
	 }
	 
	 @GetMapping("/by/brand/{brand}")
	 public ResponseEntity<ApiResponse> getAllProductsByBrandApi(@PathVariable String brand){
		 try {
			 List<Product> products = productService.getProductsByBrand(brand);
			  if (products.isEmpty()) {
				   return new ResponseEntity<ApiResponse>(new ApiResponse("No Products Available !", null),
						   HttpStatus.NOT_FOUND);
	            }
			 List<Productdto> productdtos = productService.getConvertedProducts(products);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Success !",productdtos ),
					HttpStatus.OK);
			
			} catch (Exception e) {
				return new ResponseEntity<ApiResponse>(new ApiResponse("Failed !", e.getMessage()),
						HttpStatus.NOT_FOUND);
			}
	 }
	 
	 @GetMapping("/by/category-brand")
	 public ResponseEntity<ApiResponse> getAllProductsByCategoryAndBrandApi(@RequestParam String category,@RequestParam String brand){
		 try {
			 List<Product> products = productService.getProductsByCategoryAndBrand(category,brand);
			  if (products.isEmpty()) {
				   return new ResponseEntity<ApiResponse>(new ApiResponse("No Products Available !", null),
						   HttpStatus.NOT_FOUND);
	            }
			 List<Productdto> productdtos = productService.getConvertedProducts(products);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Success !",productdtos ),
					HttpStatus.OK);
			
			} catch (Exception e) {
				return new ResponseEntity<ApiResponse>(new ApiResponse("Failed !", e.getMessage()),
						HttpStatus.NOT_FOUND);
			}
	 }
	 
	 @GetMapping("/by/brand-name")
	 public ResponseEntity<ApiResponse> getAllProductsByBrandAndNameApi(@RequestParam String brand,@RequestParam String name){
		 try {
			 List<Product> products = productService.getProductsByBrandAndName(brand,name);
			  if (products.isEmpty()) {
				   return new ResponseEntity<ApiResponse>(new ApiResponse("No Products Available !", null),
						   HttpStatus.NOT_FOUND);
	            }
			 List<Productdto> productdtos = productService.getConvertedProducts(products);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Success !",productdtos ),
					HttpStatus.OK);
			
			} catch (Exception e) {
				return new ResponseEntity<ApiResponse>(new ApiResponse("Failed !", e.getMessage()),
						HttpStatus.NOT_FOUND);
			}
	 }
	 
	 @GetMapping("/count")
	 public ResponseEntity<ApiResponse> countProductsApi(@RequestParam String brand,String name){
	    Long count =	 productService.countProductsByBrandAndName(brand, name);
	     return new ResponseEntity<ApiResponse>(new ApiResponse("Products Count ::", count),
	    		 HttpStatus.OK);
	 }
	 
	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	 @PostMapping("/add")
	 public ResponseEntity<ApiResponse> addProductApi(@RequestBody ProductRequest productRequest){
		  System.out.println("product \s"+productRequest);     
		 try {
		      Product product =  	  productService.addProduct(productRequest);
			Productdto productdto= 	productService.convertodto(product);
		      return new ResponseEntity<ApiResponse>(new ApiResponse("Add Success !", productdto),
		    		  HttpStatus.CREATED);
			} catch (Exception e) {
				
				return new ResponseEntity<ApiResponse>(new ApiResponse("Add Failure", e.getMessage()),
						HttpStatus.CONFLICT);
			}
	 }
	 
	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	 @PutMapping("/update/{id}")
	 public ResponseEntity<ApiResponse> updateProductApi(@PathVariable Integer id,@RequestBody ProductRequest productRequest ){
		 
		 try {
			   Product product =   productService.updateProduct(productRequest, id);
	          Productdto productdto = productService.convertodto(product);
		   return new ResponseEntity<ApiResponse>(new ApiResponse("Update Success !", productdto),
				   HttpStatus.CREATED);
		 } catch (ResourceNotFoundException e) {
			  return new ResponseEntity<ApiResponse>(new ApiResponse("Update Failure !", e.getMessage()),
					   HttpStatus.NOT_FOUND);
		}
	 }
	 
	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	 @DeleteMapping("/delete/{id}")
 public ResponseEntity<ApiResponse> deleteProductApi(@PathVariable Integer id ){
		 
		 try {
			     productService.deleteProductById(id);
		   return new ResponseEntity<ApiResponse>(new ApiResponse("Delete Success !", null),
				   HttpStatus.CREATED);
		 } catch (ResourceNotFoundException e) {
			  return new ResponseEntity<ApiResponse>(new ApiResponse("Delete Failure !", e.getMessage()),
					   HttpStatus.NOT_FOUND);
		}
	 }
	 
	 
	 
	 
	 
	 
}
