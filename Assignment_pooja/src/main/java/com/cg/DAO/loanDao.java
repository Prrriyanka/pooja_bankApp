package com.cg.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.beans.loan;

public interface loanDao extends JpaRepository<loan, Integer>{


	List<loan> findAllByUserEmail(String email);

	 List<loan> findByApprovalStatus(String approvalStatus);

}
