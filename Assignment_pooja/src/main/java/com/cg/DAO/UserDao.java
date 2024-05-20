package com.cg.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.beans.User;

public interface UserDao extends JpaRepository<User, String>{

	Optional<User> findByEmail(String email);
	

	

}
