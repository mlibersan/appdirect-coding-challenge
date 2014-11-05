package com.martinlibersan.appdirect.codingchallenge.security;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.martinlibersan.appdirect.codingchallenge.security.model.User;
import com.martinlibersan.appdirect.codingchallenge.security.service.CustomUserService;

/**
 * This class is implemented to do custom autuentication using spring-security.
 * User credentials are validated against information stored in a database.
 *
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger = Logger.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private CustomUserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info("authenticate ");
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		logger.info("   username " + username);
		logger.info("   password " + password);

		try {
			User user = userService.loadUserByUsername(username);

			if (user == null) {
				throw new BadCredentialsException("Username not found.");
			}

			if (!password.equals(user.getPassword())) {
				throw new BadCredentialsException("Wrong password.");
			}

			Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

			return new UsernamePasswordAuthenticationToken(user, password, authorities);
		} catch (UsernameNotFoundException e) {
			throw new BadCredentialsException("Username not found.");
		}

	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}
}