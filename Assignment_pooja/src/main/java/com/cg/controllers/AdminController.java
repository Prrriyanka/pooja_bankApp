package com.cg.controllers;

import java.security.Principal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cg.service.UserService;


@Controller()
@RequestMapping(value = "/home/admin/")
public class AdminController {

	@Autowired
	private UserService userServiceImpl;

	@Autowired
	
	@GetMapping("/index")
	public String adminHome(Model model) {
		model.addAttribute("title", "CCPL-Admin Dashboard");
		return "admin/admin";
	}

	
	
}
