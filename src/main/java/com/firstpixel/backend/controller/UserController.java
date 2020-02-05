package com.firstpixel.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.firstpixel.backend.entity.User;
import com.firstpixel.backend.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Page<User> list(
			@RequestParam("page") int page,
			@RequestParam("size") int size
			){
		PageRequest pageable = PageRequest.of(page, size);
		return  userRepository.findAll(pageable);
		
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping(value = "", method = RequestMethod.POST)
	public User save( @RequestBody User user ){
		
		return userRepository.save(user);
		
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public User edit( @RequestBody User user ){
		
		return userRepository.save(user);
		
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete( @PathVariable Long id ){
		
		 userRepository.deleteById(id);
		
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Optional<User> detail( @PathVariable Long id ){
		
		 return userRepository.findById(id);
		
	}
	
}
