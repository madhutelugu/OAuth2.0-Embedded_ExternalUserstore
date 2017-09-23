package com.zetaopt.auth;

import java.security.Principal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zetaopt.auth.config.CustomAuthenticationProvider;
import com.zetaopt.auth.config.CustomUserDetails;
import com.zetaopt.auth.entities.Role;
import com.zetaopt.auth.entities.User;
import com.zetaopt.auth.repositories.UserRepository;
import com.zetaopt.auth.services.UserService;



@SpringBootApplication
public class OAuth2AuthServerApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(OAuth2AuthServerApplication.class, args);
	}
	
	@Autowired
	private CustomAuthenticationProvider authProvider;
	
	/**
	 * Password grants are switched on by injecting an AuthenticationManager.
	 * Here, we setup the builder so that the userDetailsService is the one we coded.
	 * @param builder
	 * @param repository
	 * @throws Exception
     */
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repository, UserService service) throws Exception {
		//Setup a default user if db is empty
		/*if (repository.count()==0)
			service.save(new User("user", "user", Arrays.asList("USER", "ACTUATOR")));*/
		//builder.userDetailsService(userDetailsService(repository)).passwordEncoder(passwordEncoder);
		
		//builder.userDetailsService(userDetailsService(repository));
		
		builder.authenticationProvider(authProvider);
	}

	/**
	 * We return an istance of our CustomUserDetails.
	 * @param repository
	 * @return
     */
	private UserDetailsService userDetailsService(final UserRepository repository) {
		return username -> new CustomUserDetails(repository.findByDn(username));
	}


}
