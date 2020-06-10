package com.orekoya.userfront.service;

import java.util.Set;

import com.orekoya.userfront.domain.User;
import com.orekoya.userfront.domain.security.UserRole;

public interface UserService {
	public void save(User user);
	public User findByUsername(String username);
	public User findByEmail(String email);
	public boolean checkUserExists(String username, String email);
	public boolean checkUsernameExists(String username);
	public boolean checkEmailExists(String email);
	public User createUser(User user, Set<UserRole> userRoles);
	
	
}
