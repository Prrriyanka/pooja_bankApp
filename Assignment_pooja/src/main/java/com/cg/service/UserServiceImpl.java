package com.cg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.DAO.UserDao;
import com.cg.DAO.loanDao;
import com.cg.beans.User;
import com.cg.beans.loan;
import com.cg.exceptions.LoanDetailsNotFoundException;
import com.cg.exceptions.UserDetaislNotFoundException;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private loanDao loandao;
	
	@Override
	public User saveUser(User user) {
		user.setRole("ROLE_NORMAL_USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDao.save(user);
		return userDao.save(user);
	}

	@Override
	public Optional<User> findUserByEmail(String email){
		// TODO Auto-generated method stub
		return userDao.findByEmail(email);
	}
	
	@Override
	public loan saveHomeLoan(loan loan) {
		return loandao.save(loan);
	}

	@Override
	public List<loan> getAllLoansByEmail(String email){
		List<loan> loans=loandao.findAllByUserEmail(email);
		return loans;
	}

	@Override
	public List<loan> getAllLoansbyApprovalStatus(String approvalStatus) throws LoanDetailsNotFoundException{
		// TODO Auto-generated method stub
		List<loan> loans= loandao.findByApprovalStatus(approvalStatus);
		if(loans.isEmpty()) {
			throw new LoanDetailsNotFoundException("no loans found");
		}
		return loans;
	}
	
	@Override
	public List<loan> getAllLoans() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean approveLoan(int loanId) {
		Optional<loan> optionalloan = loandao.findById(loanId);
		if(optionalloan.isPresent()) {
			loan loanobj=optionalloan.get();
			loanobj.setApprovalStatus("Approved");
			loandao.save(loanobj);
			return true; 
			
		}
		return false;
	}
	
	@Override
	public boolean rejectLoan(int loanId) {
		Optional<loan> optionalloan = loandao.findById(loanId);
		if(optionalloan.isPresent()) {
			loan loanobj=optionalloan.get();
			loanobj.setApprovalStatus("Rejected");
			loandao.save(loanobj);
			return true; 
			
		}
		return false;
	}

	 // Method to retrieve a loan by its ID
    public Optional<loan> getLoanById(int id){
    	return loandao.findById(id);// Assuming loanRepository is an instance of a JpaRepository or similar
    }


    public loan payEMI(int loanId) {
        Optional<loan> loanOptional = loandao.findById(loanId);
        if (!loanOptional.isPresent()) {
            throw new RuntimeException("Loan not found");
        }
        loan loan = loanOptional.get();

        // Calculate the monthly interest rate
        double monthlyInterestRate = loan.getCurrentROI() / 12 / 100;

        // Calculate the current principal outstanding
        double principalOutstanding = Double.parseDouble(loan.getPrincipalOutstandingAmount());
        
        // Calculate the EMI
        double emi = loan.getMonthlyEMI();
        
        // Calculate interest part of the EMI
        double interestPayment = principalOutstanding * monthlyInterestRate;
        // Update the outstanding EMI count
        int outstandingEMICount = Integer.parseInt(loan.getOutstandingEMICount());
        // Principal part of the EMI
        double principalPayment = emi - interestPayment;
      
        
        if(outstandingEMICount>0) {
        // Update the outstanding principal
        principalOutstanding -= principalPayment;
        // Format the principalOutstandingAmount to show only two decimal places
        String formattedPrincipalOutstanding = String.format("%.2f", principalOutstanding);
        // Update the loan entity with the formatted principalOutstandingAmount
        loan.setPrincipalOutstandingAmount(formattedPrincipalOutstanding);  
        outstandingEMICount -= 1;
        loan.setOutstandingEMICount(String.valueOf(outstandingEMICount));
        
        }

        // If outstanding EMI count is 0, mark the loan as fulfilled
        if (outstandingEMICount <= 0) {
            loan.setApprovalStatus("fulfilled");
            loan.setPrincipalOutstandingAmount("0");
            loan.setOutstandingEMICount("0");
        }
        // Save the loan entity
        return loandao.save(loan);
    }


}
