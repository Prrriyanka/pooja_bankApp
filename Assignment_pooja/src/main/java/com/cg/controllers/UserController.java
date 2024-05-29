package com.cg.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cg.beans.User;
import com.cg.beans.loan;
import com.cg.exceptions.LoanDetailsNotFoundException;
import com.cg.exceptions.UserDetaislNotFoundException;
import com.cg.service.UserService;
import com.cg.service.UserServiceImpl;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping(value = "/home/user/")
public class UserController {

	@Autowired
	private UserService userServiceImpl;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
			// principal will give current logged in user name
			String userName = principal.getName();
			System.out.println(userName);

			// get user from db
			Optional<User> findBy = this.userServiceImpl.findUserByEmail(userName);
			User user = findBy.get();
			
			model.addAttribute("user", user);
		
	}

	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("title", "User Dashboard");

	
			// Get the logged-in user's email
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String email = authentication.getName();

			// Retrieve the user by email
			Optional<User> userOptional = userServiceImpl.findUserByEmail(email);
			if (userOptional.isPresent()) {
				
				User user = userOptional.get();

				// Retrieve loans by user's email
				List<loan> loans = userServiceImpl.getAllLoansByEmail(email);

				// Add the list of loans to the model
				model.addAttribute("loans", loans);
			}
		return "User/dashboard";
	}

	@GetMapping("/allhomeLoans")
	public String showallhomeLoans() {
		return "User/allhomeLoans";
	}

	@GetMapping("/newHomeLoan")
	public String applyfornewHomeLoan(@RequestParam(value = "loantype", required = false) String loantype,
			Model model) {
		model.addAttribute("loan", new loan());
		model.addAttribute("selectedloanType", loantype);
		return "User/newHomeLoan";
	}

	@PostMapping("/saveHomeLoan")
	public String saveHomeLoan(@ModelAttribute("loan") loan loan, Model model) {

		// Get the logged-in user's email
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();

		// Retrieve the user by email
		Optional<User> userOptional = userServiceImpl.findUserByEmail(email);
		// Check if the user exists
		if (userOptional.isPresent()) {
			User user = userOptional.get();

			// Set the user in the loan entity
			loan.setUser(user);
			loan.setApprovalStatus("waiting for approval");
			loan.setCurrentROI(6.0);//setting current ROI
			double monthlyInterestRate = loan.getCurrentROI() / 12 / 100;
			int totalMonths = loan.getLoanTenure() * 12;
			double emi = (loan.getTotalLoanAmount() * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalMonths)) / 
	                 (Math.pow(1 + monthlyInterestRate, totalMonths) - 1);	
			int emiInteger = (int) Math.round(emi); // Convert to integer by rounding the double value
			loan.setMonthlyEMI(emiInteger);
			
			// Set initial principal outstanding amount and EMI count
			loan.setPrincipalOutstandingAmount(String.valueOf(loan.getTotalLoanAmount()));
			loan.setOutstandingEMICount(String.valueOf(totalMonths));
			System.out.println("loan"+loan);
			model.addAttribute("Emi",emiInteger);
			
			// save emi to db or how??
		}
		
		

		loan loansaved = userServiceImpl.saveHomeLoan(loan);
		model.addAttribute("successmessage", "loan applied");
		model.addAttribute("loanDetails", loansaved);

		return "redirect:/home/user/index";
	}

	@GetMapping("/{id}/loandetails")
	public String getLoanDetails(@PathVariable("id") int id, Model model) {
		Optional<loan> loan = userServiceImpl.getLoanById(id);
		if (loan.isPresent()) {
			model.addAttribute("loan", loan.get());
			return "User/loandetails";
		} else {
			model.addAttribute("errorMessage", "Loan not found with ID: " + id);
			return "error"; 
		}

	}
	 @GetMapping("/basic_home_loan_details")
	    public String showBasicHomeLoanDetails() {
	        return "User/basic_home_loan_details"; // The Thymeleaf template to be rendered
	    }
	
	 @PostMapping("/payEMI/{loanId}")
	 public String payEMI(@PathVariable int loanId, RedirectAttributes redirectAttributes) {
	     try {
	    	 
	    	 Optional<loan> optionalLoan = userServiceImpl.getLoanById(loanId);
	    	 if (optionalLoan.isPresent()) {
	    		    loan getloan = optionalLoan.get();
	         // Check if the loan approval status is "waiting for approval"
	         if (getloan.getApprovalStatus().equalsIgnoreCase("waiting for approval")) {
	             redirectAttributes.addFlashAttribute("errorMessage", "Loan is not approved yet");
	             return "redirect:/home/user/" + loanId + "/loandetails";
	         }
	         else if(getloan.getApprovalStatus().equalsIgnoreCase("rejected")) {
	        	 redirectAttributes.addFlashAttribute("errorMessage", "Loan is Rejected");
	             return "redirect:/home/user/" + loanId + "/loandetails";
	         }
	    	 }
	         loan updatedLoan = userServiceImpl.payEMI(loanId);
	         System.out.println("Test on id: " + loanId);
	         // Add success message
	         redirectAttributes.addFlashAttribute("successMessage", "EMI payment successful");
	     } catch (RuntimeException e) {
	         // Add error message
	         redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
	     }
	     return "redirect:/home/user/" + loanId + "/loandetails";
	 }





}