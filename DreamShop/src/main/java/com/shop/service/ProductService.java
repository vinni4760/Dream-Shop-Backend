package com.shop.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shop.dto.Imagedto;
import com.shop.dto.Productdto;
import com.shop.entity.Category;
import com.shop.entity.Product;
import com.shop.exceptions.AlreadyExistException;
import com.shop.exceptions.ResourceNotFoundException;
import com.shop.repository.ICategoryRepository;
import com.shop.repository.IProductRepository;
import com.shop.repository.ImageRepository;
import com.shop.request.ProductRequest;

import jakarta.transaction.Transactional;

@Service
public class ProductService implements IProductService{
	
	@Autowired
	private IProductRepository iProductRepository;
	@Autowired
	private ICategoryRepository categoryRepository;
	@Autowired
	private ModelMapper modelmapper;

	@Autowired
	private ImageRepository imageRepository;
	
	@Override
	public Product addProduct(ProductRequest productRequest) {
		
		if (iProductRepository.existsByName(productRequest.getName())) {
		    throw new AlreadyExistException("Product Already Exists !");
		}
		             
		
	 Category category =Optional.ofNullable(categoryRepository.findByName(productRequest.getCategory().getName()))
		.orElseGet(()->{
			Category newcategory = new Category();
			newcategory.setName(productRequest.getCategory().getName());
			return categoryRepository.save(newcategory);
		});
	 productRequest.setCategory(category);
		return iProductRepository.save(createProduct(productRequest));
	}
	
	public Product createProduct(ProductRequest productdto) {
		
		return new Product(productdto.getName(), productdto.getBrand(), 
				productdto.getInventory(),
				productdto.getDescription(), 
				productdto.getPrice(),
				productdto.getCategory());
	}

	@Override
	public Product getProductByid(Integer id) {
		return iProductRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Product Not Found"));
	}

	@Transactional
	@Override
	public void deleteProductById(Integer id) {
		
		iProductRepository.findById(id)
		.ifPresentOrElse((product)->{
			product.setCategory(null);
			iProductRepository.save(product);
			iProductRepository.delete(product);
		}, ()->{
			new ResourceNotFoundException("Product Not Found");
		});
		
	}

	@Override
	public Product updateProduct(ProductRequest productdto, Integer id) {
	
		return iProductRepository.findById(id)
		.map(existingproduct->updateExistingProduct(existingproduct,productdto))
		.map(iProductRepository::save)
		.orElseThrow(()->new ResourceNotFoundException("Product Not Found"));
		
	}
	

	private Product updateExistingProduct(Product existingproduct, ProductRequest productdto) {
		existingproduct.setName(productdto.getName());
		existingproduct.setBrand(productdto.getBrand());
		existingproduct.setPrice(productdto.getPrice());
		existingproduct.setInventory(productdto.getInventory());
		existingproduct.setDescription(productdto.getDescription());
		existingproduct.setCategory(categoryRepository.findByName(productdto.getCategory().getName()));
		return existingproduct;
	}

	@Override
	public List<Product> getAllProducts() {
		return iProductRepository.findAll();
	}

	@Override
	public List<Product> getProductsByName(String name) {
		return iProductRepository.findByName(name);
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return iProductRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		return iProductRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		
		return iProductRepository.findByCategoryNameAndBrand(category,brand);
	}

	@Override
	public List<Product> getProductsByBrandAndName(String brand, String name) {
		return iProductRepository.findByBrandAndName(brand,name);
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		return iProductRepository.countByBrandAndName(brand,name);
	}
	
	@Override
	public Productdto convertodto(Product product) {
		
		Productdto productdto = modelmapper.map(product, Productdto.class);
		 List<com.shop.entity.Image> imagelist = imageRepository.findByProductId(product.getId());
		  List<Imagedto> imagedtolist =    imagelist.stream()
		      .map((image)->modelmapper.map(image, Imagedto.class))
		      .toList();
		  productdto.setImages(imagedtolist);
		 return productdto;
	}

	@Override
	public List<Productdto> getConvertedProducts(List<Product> products) {
		return products.stream()
		.map(this::convertodto)
		.toList();
	}

}
