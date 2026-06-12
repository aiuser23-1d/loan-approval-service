package com.bank;

import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;
import com.bank.loans.service.LoanApprovalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Loan Approval Service Application Tests")
class LoanApprovalServiceApplicationTests {

	@Autowired
	private LoanApprovalService loanApprovalService;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Should process loan application successfully for salaried applicant")
	void testProcessApplication_WithSalariedApplicant() {
		// Arrange
		LoanApplicationRequest request = new LoanApplicationRequest();
		request.setApplicantId("APP001");
		request.setCreditScore(750);
		request.setEmploymentStatus("SALARIED");
		request.setRequestedAmount(new BigDecimal("500000"));
		request.setAnnualIncome(new BigDecimal("600000"));

		// Act
		LoanDecisionResponse response = loanApprovalService.processApplication(request);

		// Assert
		assertNotNull(response);
		assertEquals("APPROVED", response.getStatus());
		assertEquals(new BigDecimal("7.5"), response.getInterestRate());
	}

	@Test
	@DisplayName("Should process loan application successfully for self-employed applicant")
	void testProcessApplication_WithSelfEmployedApplicant() {
		// Arrange
		LoanApplicationRequest request = new LoanApplicationRequest();
		request.setApplicantId("APP002");
		request.setCreditScore(680);
		request.setEmploymentStatus("SELF_EMPLOYED");
		request.setRequestedAmount(new BigDecimal("300000"));
		request.setAnnualIncome(new BigDecimal("800000"));

		// Act
		LoanDecisionResponse response = loanApprovalService.processApplication(request);

		// Assert
		assertNotNull(response);
		assertEquals("APPROVED", response.getStatus());
		assertEquals(new BigDecimal("13.5"), response.getInterestRate());
	}

	@Test
	@DisplayName("Should reject loan application for low credit score")
	void testProcessApplication_RejectedForLowCreditScore() {
		// Arrange
		LoanApplicationRequest request = new LoanApplicationRequest();
		request.setApplicantId("APP003");
		request.setCreditScore(500);
		request.setEmploymentStatus("SALARIED");
		request.setRequestedAmount(new BigDecimal("500000"));
		request.setAnnualIncome(new BigDecimal("400000"));

		// Act
		LoanDecisionResponse response = loanApprovalService.processApplication(request);

		// Assert
		assertNotNull(response);
		assertEquals("REJECTED", response.getStatus());
		assertNull(response.getInterestRate());
	}

}
