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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cg.beans.User;
import com.cg.beans.loan;
import com.cg.exceptions.LoanDetailsNotFoundException;
import com.cg.exceptions.UserDetaislNotFoundException;
import com.cg.service.UserService;

@Controller()
@RequestMapping(value = "/home/admin/")
public class AdminController {

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
		System.out.println(user);
		model.addAttribute("user", user);
	}

	@GetMapping("/index")
	public String adminHome(Model model) {
		model.addAttribute("title", "Admin Dashboard");
		return "admin/admin";
	}

	@GetMapping("/appliedHomeLoans")
	public String appliedHomeLoans(Model model) throws LoanDetailsNotFoundException {
		try {
			model.addAttribute("title", "Admin Dashboard");
			List<loan> loans = userServiceImpl.getAllLoansbyApprovalStatus("waiting for approval");
			if (loans.isEmpty()) {
				throw new LoanDetailsNotFoundException("no loans found");

			}
			model.addAttribute("loans", loans);

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "No loans found");
		}
		return "admin/appliedHomeLoans";
	}

	@PostMapping("/approve")
	public String approveLoan(@RequestParam("loanId") int loanId, RedirectAttributes redirectAttributes) {
		Boolean isApproved = userServiceImpl.approveLoan(loanId);
		if (isApproved) {
			redirectAttributes.addFlashAttribute("message", "Loan approved successfully.");

		} else {
			redirectAttributes.addFlashAttribute("error", "Failed to approve loan.");

		}
		return "redirect:/home/admin/appliedHomeLoans";
	}

	@PostMapping("/reject")
	public String rejectLoan(@RequestParam("loanId") int loanId, RedirectAttributes redirectAttributes) {
		boolean isRejected = userServiceImpl.rejectLoan(loanId);
		if (isRejected) {
			redirectAttributes.addFlashAttribute("message", "Loan rejected successfully.");
		} else {
			redirectAttributes.addFlashAttribute("error", "Failed to reject loan.");
		}
		return "redirect:/home/admin/appliedHomeLoans";
	}

	@GetMapping("/rejectedHomeLoans")
	public String getallRejectedHomeLoans(Model model) throws LoanDetailsNotFoundException {
		try {
			List<loan> loans = userServiceImpl.getAllLoansbyApprovalStatus("Rejected");
			if (loans.isEmpty()) {
				throw new LoanDetailsNotFoundException("no loans in rejected");
			}
			model.addAttribute("loans", loans);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "No loans in Rejected");
		}
		return "admin/rejectedHomeLoans";
	}

	@GetMapping("/approvedHomeLoans")
	public String getallApprovedHomeLoans(Model model) throws LoanDetailsNotFoundException {
		try {
			List<loan> loans = userServiceImpl.getAllLoansbyApprovalStatus("Approved");
			if (loans.isEmpty()) {
				throw new LoanDetailsNotFoundException("no loans found by this status");
			}
			model.addAttribute("loans", loans);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "No loans in Approved");
		}
		return "admin/approvedHomeLoans";
	}
	
	@GetMapping("/{id}/loandetails")
	public String getLoanDetails(@PathVariable("id") int id, Model model) {
		Optional<loan> loan = userServiceImpl.getLoanById(id);
		if (loan.isPresent()) {
			model.addAttribute("loan", loan.get());
			return "Admin/loandetails";
		} else {
			model.addAttribute("errorMessage", "Loan not found with ID: " + id);
			return "error"; 
		}
	}
	

}
