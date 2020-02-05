package com.firstpixel.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.firstpixel.backend.entity.User;
import com.firstpixel.backend.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public MyUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(username);
		if(user == null ) {
			throw new UsernameNotFoundException("Usuário ou senha incorretos");
		}
		
		return new UserRepositoryUserDetails(user);
	}
	
	private final static class UserRepositoryUserDetails extends User implements UserDetails {
		
		private UserRepositoryUserDetails(User user) {
			super(user);
		}
		
		
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			// TODO Auto-generated method stub
			return getRoles();
		}
		
		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return this.getEmail();
		}
		
		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}
		
		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			return true;
		}
		
		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}
		
		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return true;
		}
		
		@Override
		public String getPassword() {
			// TODO Auto-generated method stub
			return super.getPassword();
		}
	}
	
	
}
