package com.firstpixel.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firstpixel.backend.entity.User;
import com.firstpixel.backend.repository.UserRepository;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Page<User> list(
			@RequestParam("page") int page,
			@RequestParam("size") int size
			){
		PageRequest pageable = PageRequest.of(page, size);
		return  userRepository.findAll(pageable);
		
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public User save( @RequestBody User user ){
		
		return userRepository.save(user);
		
	}
	
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public User edit( @RequestBody User user ){
		
		return userRepository.save(user);
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete( @PathVariable Long id ){
		
		 userRepository.deleteById(id);
		
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Optional<User> detail( @PathVariable Long id ){
		
		 return userRepository.findById(id);
		
	}
	
}
