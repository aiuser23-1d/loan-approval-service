package com.bank.loans.service.strategies;

import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;
import com.bank.loans.service.LoanDecisionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Strategy for evaluating loan applications from salaried employees.
 * Applies standard credit score-based decision rules.
 */
@Service
public class SalariedLoanStrategy implements LoanDecisionStrategy {

    private static final Logger log = LoggerFactory.getLogger(SalariedLoanStrategy.class);
    private static final String EMPLOYMENT_STATUS = "SALARIED";

    @Override
    public LoanDecisionResponse evaluate(LoanApplicationRequest request) {
        log.info("Evaluating salaried applicant: {} with credit score: {}",
                request.getApplicantId(), request.getCreditScore());

        if (request.getCreditScore() == null || request.getCreditScore() < 300) {
            return new LoanDecisionResponse(null, "REJECTED", null,
                    "Credit score too low or not provided");
        }

        if (request.getCreditScore() >= 750) {
            return new LoanDecisionResponse(null, "APPROVED", new BigDecimal("7.5"),
                    "Excellent credit profile for salaried applicant");
        }

        if (request.getCreditScore() >= 600) {
            return new LoanDecisionResponse(null, "APPROVED", new BigDecimal("12.0"),
                    "Standard credit profile for salaried applicant");
        }

        return new LoanDecisionResponse(null, "REJECTED", null,
                "Credit score below minimum threshold");
    }

    @Override
    public boolean supports(String employmentStatus) {
        return EMPLOYMENT_STATUS.equalsIgnoreCase(employmentStatus);
    }
}
