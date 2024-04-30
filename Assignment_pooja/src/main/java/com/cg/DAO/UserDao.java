package com.cg.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.beans.AdminUser;
import com.cg.beans.User;

public interface UserDao extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);

	Optional<AdminUser> findById(String username);

}
