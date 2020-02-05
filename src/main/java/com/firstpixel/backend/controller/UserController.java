package com.firstpixel.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firstpixel.backend.entity.User;
import com.firstpixel.backend.repository.UserRepository;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<User> list(){
		return userRepository.findAll();
	}
	
}
