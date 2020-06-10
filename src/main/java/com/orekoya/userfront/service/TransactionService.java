package com.orekoya.userfront.service;

import java.security.Principal;
import java.util.List;

import com.orekoya.userfront.domain.PrimaryAccount;
import com.orekoya.userfront.domain.PrimaryTransaction;
import com.orekoya.userfront.domain.Recipient;
import com.orekoya.userfront.domain.SavingsAccount;
import com.orekoya.userfront.domain.SavingsTransaction;

public interface TransactionService {
	
	List<PrimaryTransaction> findPrimaryTransactionList(String username);
	
	List<SavingsTransaction> findSavingsTransactionList(String username);
	
	void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction);
	
	void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);
	
	void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction);
	
	void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);
	
	void betweenAccountsTransfer(String transaferFrom, String transaferTo, String Amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception;

	public List<Recipient> findRecipientList(Principal principal);
	
	public Recipient saveRecipient(Recipient recipient);
	
	public Recipient findRecipientByName(String recipientName);
	
	public void deleteRecipientByName(String recipientName);
	
	void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount);
}
