package com.orekoya.userfront.dao;

import org.springframework.data.repository.CrudRepository;

import com.orekoya.userfront.domain.PrimaryAccount;

public interface PrimaryAccountDao extends CrudRepository<PrimaryAccount, Long> {
	
	PrimaryAccount findByAccountNumber (int accountNumber);
}
