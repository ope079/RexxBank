package com.orekoya.userfront.dao;

import org.springframework.data.repository.CrudRepository;

import com.orekoya.userfront.domain.SavingsAccount;

public interface SavingsAccountDao extends CrudRepository<SavingsAccount, Long> {
	
	SavingsAccount findByAccountNumber (int accountNumber);
}
