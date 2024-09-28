package com.shop.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shop.dto.Userdto;
import com.shop.entity.Roles;
import com.shop.entity.User;
import com.shop.exceptions.AlreadyExistException;
import com.shop.exceptions.ResourceNotFoundException;
import com.shop.repository.IRoleRepository;
import com.shop.repository.IUserRepository;
import com.shop.request.CreateUserRequest;
import com.shop.request.UpdateUserRequest;

@Service
public class UserService implements IUserService{

	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private IRoleRepository iRoleRepository;
	@Override
	public User getUserById(Integer user_id) {
		return userRepository.findById(user_id)
		.orElseThrow(()->new ResourceNotFoundException("User Not Found !"));
	}

	@Override
	public User createUser(CreateUserRequest createUserRequest) {
		Roles roles = iRoleRepository.findByName("ROLE_USER");
		Set<Roles> userRoles = new HashSet<Roles>();
		userRoles.add(roles);
	  return	Optional.of(createUserRequest)
		.filter(request->!userRepository.existsByEmail(createUserRequest.getEmail()))
		.map(req->{
			User user = new User();
			user.setFirstName(createUserRequest.getFirstName());
			user.setLastName(createUserRequest.getLastName());
			user.setRoles(userRoles);
			user.setEmail(createUserRequest.getEmail());
			user.setPassword(encoder.encode(createUserRequest.getPassword()));
			return userRepository.save(user);
		})
		.orElseThrow(()->new AlreadyExistException("!Oops"+createUserRequest.getEmail()+"\sAlready Exists"));
	}

	@Override
	public User updateUser(UpdateUserRequest request, Integer user_id) {
	 return	userRepository.findById(user_id)
		.map(user->{
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
		  return	userRepository.save(user);
		}).orElseThrow(()->new ResourceNotFoundException("User Not Found !"));
	}

	@Override
	public void deleteUserbyId(Integer user_id) {
		 
		userRepository.findById(user_id)
		.ifPresentOrElse(userRepository::delete, ()->{
			throw new ResourceNotFoundException("User Not Found !");});
	}

	@Override
	public Userdto convertodto(User user) {
		return mapper.map(user, Userdto.class);
	}
	
	

}
