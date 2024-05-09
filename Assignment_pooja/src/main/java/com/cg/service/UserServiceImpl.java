package com.cg.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.DAO.UserDao;
import com.cg.beans.User;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserDao userDao;
	
	@Override
	public User saveUser(User user) {
		user.setRole("ROLE_NORMAL_USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDao.save(user);
		return userDao.save(user);
	}

	@Override
	public Optional<User> findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userDao.findByEmail(email);
	}

}
