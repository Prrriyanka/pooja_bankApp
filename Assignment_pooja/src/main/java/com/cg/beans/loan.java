package com.cg.beans;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bankApp_loan")
public class loan {

	
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	private String name;
	private String surname;
	private String address;
	private String loantype;
	private String approvalStatus;
	private String nominee;
	private Double totalLoanAmount;
	private Integer loanTenure;
	private Double currentROI;
	private String principalOutstandingAmount;
	private String outstandingEMICount;
	private int monthlyEMI;
	private Date creationDate; // Add this field
	
	
	 @ManyToOne
	    @JoinColumn(name = "user_email", nullable = false)
	    private User user;
	
	
	// Constructor to set creationDate
	    public loan() {
	        this.creationDate = new Date(); // Initialize with current date/time
	    }


    public loan(int id, String name, String surname, String address, String loantype, String approvalStatus,
			String nominee, Double totalLoanAmount, Integer loanTenure, Double currentROI,
			String principalOutstandingAmount, String outstandingEMICount, int monthlyEMI, Date creationDate,
			User user) {
		super();
		Id = id;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.loantype = loantype;
		this.approvalStatus = approvalStatus;
		this.nominee = nominee;
		this.totalLoanAmount = totalLoanAmount;
		this.loanTenure = loanTenure;
		this.currentROI = currentROI;
		this.principalOutstandingAmount = principalOutstandingAmount;
		this.outstandingEMICount = outstandingEMICount;
		this.monthlyEMI = monthlyEMI;
		this.creationDate = creationDate;
		this.user = user;
	}




	public int getId() {
		return Id;
	}


	public void setId(int id) {
		Id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getLoantype() {
		return loantype;
	}


	public void setLoantype(String loantype) {
		this.loantype = loantype;
	}


	public String getApprovalStatus() {
		return approvalStatus;
	}


	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}



	public String getNominee() {
		return nominee;
	}



	public void setNominee(String nominee) {
		this.nominee = nominee;
	}



	public Double getTotalLoanAmount() {
		return totalLoanAmount;
	}



	public void setTotalLoanAmount(Double totalLoanAmount) {
		this.totalLoanAmount = totalLoanAmount;
	}



	public Integer getLoanTenure() {
		return loanTenure;
	}



	public void setLoanTenure(Integer loanTenure) {
		this.loanTenure = loanTenure;
	}



	public Double getCurrentROI() {
		return currentROI;
	}



	public void setCurrentROI(Double currentROI) {
		this.currentROI = currentROI;
	}



	public String getPrincipalOutstandingAmount() {
		return principalOutstandingAmount;
	}



	public void setPrincipalOutstandingAmount(String principalOutstandingAmount) {
		this.principalOutstandingAmount = principalOutstandingAmount;
	}



	public String getOutstandingEMICount() {
		return outstandingEMICount;
	}



	public void setOutstandingEMICount(String outstandingEMICount) {
		this.outstandingEMICount = outstandingEMICount;
	}



	public int getMonthlyEMI() {
		return monthlyEMI;
	}



	public void setMonthlyEMI(int monthlyEMI) {
		this.monthlyEMI = monthlyEMI;
	}



	@Override
	public String toString() {
		return "loan [Id=" + Id + ", name=" + name + ", surname=" + surname + ", address=" + address + ", loantype="
				+ loantype + ", approvalStatus=" + approvalStatus + ", nominee=" + nominee + ", totalLoanAmount="
				+ totalLoanAmount + ", loanTenure=" + loanTenure + ", currentROI=" + currentROI
				+ ", principalOutstandingAmount=" + principalOutstandingAmount + ", outstandingEMICount="
				+ outstandingEMICount + ", monthlyEMI=" + monthlyEMI + ", user=" + user + "]";
	}







	public Date getCreationDate() {
		return creationDate;
	}







	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}





	
	

}
