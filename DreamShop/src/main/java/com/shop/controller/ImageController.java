package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.PutExchange;

import com.shop.dto.Imagedto;
import com.shop.entity.Image;
import com.shop.exceptions.ResourceNotFoundException;
import com.shop.response.ApiResponse;
import com.shop.service.ImageService;

@RestController
@RequestMapping("/shop/image")
public class ImageController {
	
	@Autowired
	private ImageService imageService;

	@PostMapping("/upload")
	 public ResponseEntity<ApiResponse> uploadImages(@RequestParam List<MultipartFile> files,
			 @RequestParam("id") Integer product_id){
		 
		 try {			
			 List<Imagedto> imagelist  =	 imageService.saveImages(files, product_id);
			 return new ResponseEntity<ApiResponse>(new ApiResponse("Upload Success !", imagelist),
					 HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
				    new ApiResponse("Upload Failed!", e.getMessage()),
				    HttpStatus.INTERNAL_SERVER_ERROR
				);

			}
		 
	 }
	
	
	  @GetMapping("/download/{imageId}")
	    public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Integer imageId) {
	        try {
	            // Fetch image data from the service
	            Image image = imageService.getImageById(imageId);

	            // Convert Blob to ByteArrayResource
	            ByteArrayResource resource = new ByteArrayResource(
	                image.getImage().getBytes(1, (int) image.getImage().length())
	            );

	            // Build the response entity
	            return ResponseEntity.ok()
	                    .contentType(org.springframework.http.MediaType.parseMediaType(image.getFiletype())) // Set MIME type
	                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"") // Set the Content-Disposition header
	                    .body(resource); // Include the image content in the body

	        } catch (Exception e) {
	            // Handle exceptions (like image not found, IO exceptions, etc.)
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	  
	  @PutMapping("/update/{id}")
	  public ResponseEntity<ApiResponse> updateProductImage(@PathVariable Integer id,@RequestParam MultipartFile file){
		 
		  try {
				   imageService.updateImage(file, id);
				   return new ResponseEntity<ApiResponse>(new ApiResponse("Update Success !", null),
							 HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Update Failed !", e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
	  }
	  
	  @DeleteExchange("/delete/{id}")
	  public ResponseEntity<ApiResponse> deleteProductImageApi(@PathVariable Integer id){
		  try {
			  imageService.deleteImageById(id);
			  return new ResponseEntity<ApiResponse>(new ApiResponse("delete Success !", null),
					  HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("delete Failed !", e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
	  }
	  
	  
	
	
}

