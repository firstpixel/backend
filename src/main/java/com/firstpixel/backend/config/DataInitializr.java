package com.firstpixel.backend.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.firstpixel.backend.entity.Role;
import com.firstpixel.backend.entity.User;
import com.firstpixel.backend.repository.RoleRepository;
import com.firstpixel.backend.repository.UserRepository;

@Component
public class DataInitializr implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		List<User> users = userRepository.findAll();
		if(users.isEmpty()) {
			this.createUser("Gil B", "gilbeyruth@gmail.com", passwordEncoder.encode("1234"), "ROLE_USER");
			this.createUser("Gil B", "admin", passwordEncoder.encode("1234"), "ROLE_ADMIN");
			
		}
		
		
	}
	
	public void createUser(String name, String email, String password, String role) {
		Role roleObject = new Role();
		roleObject.setName(role);
		
		roleRepository.save(roleObject);
		
		List<Role> roleList = Arrays.asList(roleObject);
		
		User user = new User(name, email, password, roleList);
		userRepository.save(user);
		
	}

}
