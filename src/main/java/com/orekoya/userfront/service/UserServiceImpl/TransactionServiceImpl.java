package com.orekoya.userfront.service.UserServiceImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orekoya.userfront.dao.PrimaryAccountDao;
import com.orekoya.userfront.dao.PrimaryTransactionDao;
import com.orekoya.userfront.dao.RecipientDao;
import com.orekoya.userfront.dao.SavingsAccountDao;
import com.orekoya.userfront.dao.SavingsTransactionDao;
import com.orekoya.userfront.domain.PrimaryAccount;
import com.orekoya.userfront.domain.PrimaryTransaction;
import com.orekoya.userfront.domain.Recipient;
import com.orekoya.userfront.domain.SavingsAccount;
import com.orekoya.userfront.domain.SavingsTransaction;
import com.orekoya.userfront.domain.User;
import com.orekoya.userfront.service.TransactionService;
import com.orekoya.userfront.service.UserService;

@Service	
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PrimaryTransactionDao primaryTransactionDao;
	
	@Autowired
	SavingsTransactionDao savingsTransactionDao;
	
	@Autowired
	PrimaryAccountDao primaryAccountDao;
	
	@Autowired
	SavingsAccountDao savingsAccountDao;
	
	@Autowired
	RecipientDao recipientDao;
	
	public List<PrimaryTransaction> findPrimaryTransactionList(String username){
		User user = userService.findByUsername(username);
		List<PrimaryTransaction> primaryTransactionList = user.getPrimaryAccount().getPrimaryTransactionList();
				
		return primaryTransactionList;
	}
	
	public List<SavingsTransaction> findSavingsTransactionList(String username) {
		User user = userService.findByUsername(username);
		List<SavingsTransaction> savingsTransactionList = user.getSavingsAccount().getSavingsTransactionList();
		
		return savingsTransactionList;
	}
	
	public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction) {
		primaryTransactionDao.save(primaryTransaction);
	}
	
	public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {
		savingsTransactionDao.save(savingsTransaction);
	}
	
	public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction) {
		primaryTransactionDao.save(primaryTransaction);
	}
	
	public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {
		savingsTransactionDao.save(savingsTransaction);
	}
	
	public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception {
			if (transferFrom.equalsIgnoreCase("Primary") && transferTo.equalsIgnoreCase("Savings")) {
				primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
				savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
				primaryAccountDao.save(primaryAccount);
				savingsAccountDao.save(savingsAccount);
				
				Date date = new Date();
				
				PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Between account transafer from " +transferFrom+ " to " +transferTo, "Account", "Finished", Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount);
				primaryTransactionDao.save(primaryTransaction);
				
		} else if (transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Primary")) {
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);
			savingsAccountDao.save(savingsAccount);
			
			Date date = new Date();
			
			SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Between account transafer from " +transferFrom+ " to " +transferTo, "Transfer", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);
			savingsTransactionDao.save(savingsTransaction);
		} else {
			throw new Exception("Invalid Transfer");
		}
			
	}
	
	public List<Recipient> findRecipientList(Principal principal) {
		String username = principal.getName();
		List<Recipient> recipientList = recipientDao.findAll().stream()
				.filter(recipient -> username.contentEquals(recipient.getUser().getUsername()))
				.collect(Collectors.toList());
		return recipientList;
	}
	
	public Recipient saveRecipient(Recipient recipient) {
		return recipientDao.save(recipient);
	}
	
	public Recipient findRecipientByName(String recipientName) {
		return recipientDao.findByName(recipientName);
	}
	
	public void deleteRecipientByName(String recipientName) {
		recipientDao.deleteByName(recipientName);
	}
	
	public void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) {
		if (accountType.equalsIgnoreCase("Primary")) {
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);
			
			Date date = new Date();
			
			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Transfer to recipient "+recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount);
			primaryTransactionDao.save(primaryTransaction);
		} else if (accountType.equalsIgnoreCase("Savings")) {
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			savingsAccountDao.save(savingsAccount);
			
			Date date = new Date();
			
			SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Transfer to recipient "+recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);
			savingsTransactionDao.save(savingsTransaction);
		}
	}
}
