package com.cg.service;

import java.util.List;
import java.util.Optional;

import com.cg.beans.User;
import com.cg.beans.loan;
import com.cg.exceptions.LoanDetailsNotFoundException;
import com.cg.exceptions.UserDetaislNotFoundException;

public interface UserService {

	public User saveUser(User user) ;

	public Optional<User> findUserByEmail(String email);
	
	public loan saveHomeLoan(loan loan);


	public List<loan> getAllLoansByEmail(String email);

	public List<loan> getAllLoans() throws LoanDetailsNotFoundException;

	List<loan> getAllLoansbyApprovalStatus(String approvalStatus) throws LoanDetailsNotFoundException;

	public Boolean approveLoan(int loanId);

	public boolean rejectLoan(int loanId);

	Optional<loan> getLoanById(int id);

	public loan payEMI(int loanId);

}
