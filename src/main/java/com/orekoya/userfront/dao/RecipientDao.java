package com.orekoya.userfront.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.orekoya.userfront.domain.Recipient;

public interface RecipientDao extends CrudRepository <Recipient, Long> {
	
	List<Recipient> findAll();
	
	Recipient findByName(String recipientname);
	
	void deleteByName(String recipientName);
}
