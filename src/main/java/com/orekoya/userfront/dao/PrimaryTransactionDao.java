package com.orekoya.userfront.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.orekoya.userfront.domain.PrimaryTransaction;

public interface PrimaryTransactionDao extends CrudRepository<PrimaryTransaction, Long> {
	
	List<PrimaryTransaction> findAll();
	
}
