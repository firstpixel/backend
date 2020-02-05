package com.firstpixel.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import com.firstpixel.backend.service.MyUserDetailsService;

@Configuration
@EnableResourceServer
public class OAuth2ServerConfiguration extends ResourceServerConfigurerAdapter {
	
	private static final String RESOURCE_ID = "restservice";
	private static final String CLIENT_SECRET_KEY = "12345";
	private static final String CLIENT_ID = "client";
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.and().authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.anyRequest().fullyAuthenticated();
		
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(RESOURCE_ID);
	}
	
	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
		
		private TokenStore tokenStore = new InMemoryTokenStore();
		
		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;
		
		@Autowired
		private MyUserDetailsService userDetailsService;
		
		@Autowired
		private PasswordEncoder passwordEncoder;
		
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endPointsConfigurer) throws Exception {
			endPointsConfigurer.tokenStore(this.tokenStore)
					.authenticationManager(this.authenticationManager)
					.userDetailsService(this.userDetailsService);
		}
		
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
			
			clients.inMemory()
				.withClient(CLIENT_ID)
				.authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("all")
				.refreshTokenValiditySeconds(300000)
				.resourceIds(RESOURCE_ID)
				.secret(passwordEncoder.encode(CLIENT_SECRET_KEY))
				.accessTokenValiditySeconds(5000);	
		}
		
		@Bean
		@Primary
		public DefaultTokenServices tokenServices() {
			DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
			defaultTokenServices.setSupportRefreshToken(true);
			defaultTokenServices.setTokenStore(this.tokenStore);
			return defaultTokenServices;
		}
		
	}
	
}
