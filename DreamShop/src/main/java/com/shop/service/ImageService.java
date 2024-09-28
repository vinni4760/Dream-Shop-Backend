package com.shop.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.Imagedto;
import com.shop.entity.Image;

public interface ImageService {
	
	Image getImageById(Integer id);
	void deleteImageById(Integer id);
	List<Imagedto> saveImages(List<MultipartFile> files,Integer product_id);
	void updateImage(MultipartFile file,Integer id);
  List<Imagedto> getProductImages(Integer id);
}
