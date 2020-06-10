package com.orekoya.userfront.dao;

import org.springframework.data.repository.CrudRepository;

import com.orekoya.userfront.domain.User;

public interface UserDao extends CrudRepository<User, Long> {
	User findByUsername(String username);
	User findByEmail(String email);
}
