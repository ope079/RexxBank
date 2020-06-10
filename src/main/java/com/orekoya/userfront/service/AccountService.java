package com.orekoya.userfront.service;

import java.security.Principal;

import com.orekoya.userfront.domain.PrimaryAccount;
import com.orekoya.userfront.domain.SavingsAccount;

public interface AccountService {
	public PrimaryAccount createPrimaryAccount();
	public SavingsAccount createSavingsAccount();
	public void deposit(String accountType, double amount, Principal principal);
	public void withdraw(String accountType, double amount, Principal principal);
}
