package com.cg.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cg.DAO.UserDao;
import com.cg.beans.AdminUser;


public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<AdminUser> findById = userRepo.findById(username);
		if (findById.isEmpty()) {
			throw new UsernameNotFoundException("Could not found User !!");
		}

		// if user is not null then we are returning a customeUserDetail who is
		// authorize based on role
		AdminUser user = findById.get();
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		return customUserDetails;

	}

}
