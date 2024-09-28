package com.shop.service;

import java.util.List;

import com.shop.dto.Productdto;
import com.shop.entity.Product;
import com.shop.request.ProductRequest;

public interface IProductService {
	
	Product addProduct(ProductRequest productRequest);
	Product getProductByid(Integer id);
	void deleteProductById(Integer id);
	Product updateProduct(ProductRequest productRequest,Integer id);
	List<Product> getAllProducts();
	List<Product> getProductsByName(String name);
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByCategoryAndBrand(String category,String brand);
    List<Product> getProductsByBrandAndName(String brand, String name);
    List<Productdto> getConvertedProducts(List<Product> products);
    Long countProductsByBrandAndName(String brand, String name);
	Productdto convertodto(Product product);

}
