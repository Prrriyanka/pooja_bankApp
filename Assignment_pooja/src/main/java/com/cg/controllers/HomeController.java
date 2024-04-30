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

	@GetMapping("/login")
	public String loginpage(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	
	
	
	
	
	@PostMapping("/dologin")
	public String loginuser(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	
	
	
	
	
	
	
	
	
	
	
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


			return "redirect:/login";
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
	
}
