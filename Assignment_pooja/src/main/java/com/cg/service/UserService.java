package com.cg.service;

import java.util.Optional;

import com.cg.beans.User;

public interface UserService {

	public User saveUser(User user) ;

	public Optional<User> findUserByEmail(String email);

}
