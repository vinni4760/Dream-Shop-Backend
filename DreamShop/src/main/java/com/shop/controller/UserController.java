package com.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.Userdto;
import com.shop.entity.User;
import com.shop.request.CreateUserRequest;
import com.shop.request.UpdateUserRequest;
import com.shop.response.ApiResponse;
import com.shop.service.IUserService;

@RestController()
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	@GetMapping("/get")
	public ResponseEntity<ApiResponse> getUserByIdApi(@RequestParam Integer id){
		
		try {
		User user =	userService.getUserById(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Get User Success !", user),
				HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Get User Failed !", e.getMessage()),
					HttpStatus.NOT_FOUND);
		}		
	}
	
	@PostMapping("/new")
	public ResponseEntity<ApiResponse> getUserByIdApi(@RequestBody CreateUserRequest user){
		
		try {
			User user2 =  userService.createUser(user);
		    Userdto userdto = userService.convertodto(user2);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Create User Success !", userdto),
				HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Create User Failed !", e.getMessage()),
					HttpStatus.NOT_FOUND);
		}		
	}
	
	@PutMapping("/update")
	public ResponseEntity<ApiResponse> getupdateUserApi(@RequestBody UpdateUserRequest request,@RequestParam Integer id){
		
		try {
			User user = userService.updateUser(request, id);
			Userdto userdto = userService.convertodto(user);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Update Success !", userdto),
				HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(" User Update Failed !", e.getMessage()),
					HttpStatus.NOT_FOUND);
		}		
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse> deleteUserByIdApi(@RequestParam Integer id){
		
		try {
		userService.deleteUserbyId(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Delete Success !", null),
				HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("User Delete Failed !", e.getMessage()),
					HttpStatus.NOT_FOUND);
		}		
	}
	
	
	
}
