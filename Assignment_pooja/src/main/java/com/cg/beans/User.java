package com.cg.beans;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;



@Entity
@Table(name = "bankApp_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Pattern(regexp = "^\\s*[a-zA-Z]+(['\\s-][a-zA-Z]+)*\\s*$", message = "Please enter a valid name with only letters")
	private String fname;
	@Column(unique = true)
	@NotBlank(message = "Please enter an email")
	@Email(message = "Please provide a valid email address")
	private String email;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character")
	private String password;
	@Transient
	private String cpassword;
	private String role;
	@Column(nullable = false)
	@NotNull(message = "Date of Birth is required")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date DOB;
	@NotBlank(message = "Please select a Gender")
	private String gender;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Date getDOB() {
		return DOB;
	}
	public void setDOB(Date dOB) {
		DOB = dOB;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCpassword() {
		return cpassword;
	}
	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}
	public User(int id, String fname, String email, String password, String role, Date dOB, String gender) {
		super();
		this.id = id;
		this.fname = fname;
		this.email = email;
		this.password = password;
		this.role = role;
		DOB = dOB;
		this.gender = gender;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
