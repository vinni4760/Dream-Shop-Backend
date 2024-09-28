package com.shop.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.Imagedto;
import com.shop.entity.Image;
import com.shop.entity.Product;
import com.shop.exceptions.ResourceNotFoundException;
import com.shop.repository.ImageRepository;

@Service
public class ImageServiceimpl implements ImageService {
	
	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private IProductService iProductService;
	
	@Override
	public Image getImageById(Integer id) {
		return imageRepository.findById(id)
				.orElseThrow(()->{throw new ResourceNotFoundException("Image Not Found With Id"+id);});
	}

	@Override
	public void deleteImageById(Integer id) {
		
		imageRepository.findById(id)
		.ifPresentOrElse(imageRepository::delete,
				()->{
				throw new ResourceNotFoundException("Image Not Found With Id:\s"+id);
	   });
					
		
	}

	@Override
	public List<Imagedto> saveImages(List<MultipartFile> files, Integer product_id) {
		
	    Product product =	iProductService.getProductByid(product_id);
		
		List<Imagedto> savedImages = new ArrayList<Imagedto>();
		try {
		for(MultipartFile file : files) {
			Image image = new Image();
			image.setFilename(file.getOriginalFilename());
			image.setFiletype(file.getContentType());
			image.setImage(new SerialBlob(file.getBytes()));
			image.setProduct(product);
			
			String builddownloadUrl = " /shop/image/download/";
			String downloadUrl = builddownloadUrl+image.getId();
			image.setDownloadurl(downloadUrl);
		 Image savedimage = 	imageRepository.save(image);
		 savedimage.setDownloadurl(builddownloadUrl+savedimage.getId());
		 imageRepository.save(savedimage);
			
		  Imagedto imagedto = new Imagedto();
		  imagedto.setId(savedimage.getId());
		  imagedto.setFileName(savedimage.getFilename());
		  imagedto.setDownloadUrl(savedimage.getDownloadurl());
		  
		  savedImages.add(imagedto);
			
			}
		}
		catch (SQLException |IOException e) {
				e.printStackTrace();
			}
		
		 
		
		return savedImages;
	}

	@Override
	public void updateImage(MultipartFile file, Integer id) {
		
		 Image image = getImageById(id);
		 
		 try {
		 image.setFilename(file.getOriginalFilename());
		 image.setFiletype(file.getContentType());
			image.setImage(new SerialBlob(file.getBytes()));
			imageRepository.save(image);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		 
	}

	@Override
	public List<Imagedto> getProductImages(Integer id) {
		
		return null;
	}

}
