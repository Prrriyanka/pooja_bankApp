package com.cg.controllers;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cg.beans.User;
import com.cg.service.UserService;

import jakarta.validation.Valid;



@Controller
public class HomeController {

	@Autowired
	private UserService userService;



	@GetMapping("/")
	public String homepage(Model model) {
		model.addAttribute("title", "bankingApp-home");
		return "index";
	}

	
/////////////////////////// LOGIN PART //////////////////////////////////////////

	
	@GetMapping("/login")
	public String loginpage(@RequestParam(name = "param", required = false) String param,Model model) {
	    System.out.println("Parameter received in loginpage method: " + param);

		model.addAttribute("user", new User());
		return "login";
	}
	
	
	@PostMapping("/dologin")
	public String loginuser(Model model) {
	    model.addAttribute("user", new User());
		return "login";
	}
	
	/////////////////////////// REGISTER PART //////////////////////////////////////////
	
	
	@GetMapping("/register")
	public String registrationpage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/do_register")
	public String saveuser(@Valid @ModelAttribute("user")User user,BindingResult result,Model model) {

		try {
			//TODO: process POST request
			if (result.hasErrors()) {
				// If there are errors, return the registration form with errors
				// and the model attributes intact
				return "register"; // Change "register" to the name of your registration form view
			}
			// Check if passwords match
			if (!user.getPassword().equals(user.getCpassword())) {
				// If passwords don't match, add error message to model and return the registration form
				model.addAttribute("errorMessage", "Password and confirm password do not match");
				return "register";
			}
			//Duplicate email  Validation
			Optional<User> findByemailid = userService.findUserByEmail(user.getEmail());
			if(findByemailid.isPresent()) {
				 throw new SQLException("Duplicate Email Registration");
			}

			model.addAttribute("user", user);
			User u1= userService.saveUser(user);


			return "redirect:/home/user/index";
			}
		catch(SQLException s) {
	    	 s.printStackTrace();
	    	 model.addAttribute("errorMessage", "Duplicate Email Registration");
	    	 return "register";
		}
			
		catch (Exception e) {
			// TODO: handle exception
			 // Catch any other unexpected exception
	        model.addAttribute("errorMessage", e.getMessage());
	        return "register";
		}

	}
	
/////////////////////////// CALCULATE EMI //////////////////////////////////////////

	
	
	@PostMapping("/calculateEMI")
	public String CalculateEMI(@RequestParam("loanAmount") double loanAmount,@RequestParam("tenure") int tenure, @RequestParam("interestrate") double interestrate,Model model)
	{
		
		double monthlyInterestRate = interestrate / 12 / 100;
		int totalMonths = tenure * 12;
		double emi = (loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalMonths)) / 
                 (Math.pow(1 + monthlyInterestRate, totalMonths) - 1);	
		int emiInteger = (int) Math.round(emi); // Convert to integer by rounding the double value

		model.addAttribute("emi",emiInteger);
		return "index";
		
		
	}
	
	
}
