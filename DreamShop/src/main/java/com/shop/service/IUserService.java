package com.shop.service;

import com.shop.dto.Userdto;
import com.shop.entity.User;
import com.shop.request.CreateUserRequest;
import com.shop.request.UpdateUserRequest;

public interface IUserService {
     
	User getUserById(Integer user_id);
	User createUser(CreateUserRequest createUserRequest );
    User updateUser(UpdateUserRequest request,Integer user_id);
    void deleteUserbyId(Integer user_id);
    Userdto convertodto(User user);
}
