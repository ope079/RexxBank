package com.orekoya.userfront.service.UserServiceImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orekoya.userfront.dao.PrimaryAccountDao;
import com.orekoya.userfront.dao.SavingsAccountDao;
import com.orekoya.userfront.domain.PrimaryAccount;
import com.orekoya.userfront.domain.PrimaryTransaction;
import com.orekoya.userfront.domain.SavingsAccount;
import com.orekoya.userfront.domain.SavingsTransaction;
import com.orekoya.userfront.domain.User;
import com.orekoya.userfront.service.AccountService;
import com.orekoya.userfront.service.TransactionService;
import com.orekoya.userfront.service.UserService;


@Service
public class AccountServiceImpl implements AccountService {
	
	private static int nextAccountNumber = 11115555;
	
	@Autowired
	private PrimaryAccountDao primaryAccountDao;
	
	@Autowired
	private SavingsAccountDao savingsAccountDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionService transactionService;
	
	
	
	public PrimaryAccount createPrimaryAccount() {
		PrimaryAccount primaryAccount = new PrimaryAccount();
		primaryAccount.setAccountBalance(new BigDecimal(0.0));
		primaryAccount.setAccountNumber(accountGen());
		
		primaryAccountDao.save(primaryAccount);
		
		return primaryAccountDao.findByAccountNumber(primaryAccount.getAccountNumber());
	}
	
	public SavingsAccount createSavingsAccount() {
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setAccountBalance(new BigDecimal(0.0));
		savingsAccount.setAccountNumber(accountGen());
		
		savingsAccountDao.save(savingsAccount);
		
		return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
		
	}
	
	public void deposit(String accountType, double amount, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		
		if(accountType.equalsIgnoreCase("Primary")){
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);
			
			Date date = new Date();
			
			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Deposit to Primary Account", "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
			transactionService.savePrimaryDepositTransaction(primaryTransaction);
			
				} else if (accountType.equalsIgnoreCase("Savings")) {
					SavingsAccount savingsAccount = user.getSavingsAccount();
					savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
					savingsAccountDao.save(savingsAccount);
					
					Date date = new Date();
					SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to Savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
					transactionService.saveSavingsDepositTransaction(savingsTransaction);
					
				}
		
	}
	
	public void withdraw(String accountType, double amount, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		
		if(accountType.equalsIgnoreCase("Primary")){
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);
			
			Date date = new Date();
			
			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Withdraw from Primary Account", "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
			transactionService.savePrimaryWithdrawTransaction(primaryTransaction);
			
				} else if (accountType.equalsIgnoreCase("Savings")) {
					SavingsAccount savingsAccount = user.getSavingsAccount();
					savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
					savingsAccountDao.save(savingsAccount);
					
					Date date = new Date();
					SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Withdraw from Savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
					transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
				}
		
	}
	
	private int accountGen() {
		return ++nextAccountNumber;
	}
}
