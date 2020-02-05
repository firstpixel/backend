package com.firstpixel.backend.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.firstpixel.backend.entity.User;
import com.firstpixel.backend.repository.UserRepository;

@Component
public class DataInitializr implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		List<User> users = userRepository.findAll();
		if(users.isEmpty()) {
			this.createUser("Gil B", "gilbeyruth@gmail.com", "1234");
			this.createUser("Gil Beyruth", "gilbeyruth@gmail.com", "1234");
			this.createUser("Gil Bey", "gilbeyruth@gmail.com", "1234");
			
		}
		
		
	}
	
	public void createUser(String name, String email, String password) {
		User user = new User(name, email, password);
		userRepository.save(user);
		
	}

}
