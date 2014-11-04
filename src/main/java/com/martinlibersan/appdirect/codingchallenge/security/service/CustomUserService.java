package com.martinlibersan.appdirect.codingchallenge.security.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.martinlibersan.appdirect.codingchallenge.security.model.Role;
import com.martinlibersan.appdirect.codingchallenge.security.model.User;
import com.martinlibersan.appdirect.codingchallenge.web.model.Person;
import com.martinlibersan.appdirect.codingchallenge.web.service.PersonService;

@Service
public class CustomUserService implements UserDetailsService {

	private static final Logger logger = Logger.getLogger(CustomUserService.class);

	@Autowired
	private PersonService personService;

	@Override
	public User loadUserByUsername(final String username) throws UsernameNotFoundException {
		logger.info("loadUserByUsername " + username);

		Person userFind = personService.findPersonByOpenId(username);
		if (userFind != null) {
			User user = new User();
			user.setFirstName(userFind.getFirstName());
			user.setLastName(userFind.getLastName());
			user.setUsername(username);

			Role r = new Role();
			r.setName("ROLE_USER");
			List<Role> roles = new ArrayList<Role>();
			roles.add(r);
			user.setAuthorities(roles);
			return user;
		} else {
			throw new UsernameNotFoundException(username);
		}

	}
}
