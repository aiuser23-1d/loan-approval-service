package com.bank.loans.service.strategies;

import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;
import com.bank.loans.service.LoanDecisionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Strategy for evaluating loan applications from self-employed individuals.
 * Applies standard credit score-based decision rules with self-employment note.
 */
@Service
public class SelfEmployedLoanStrategy implements LoanDecisionStrategy {

    private static final Logger log = LoggerFactory.getLogger(SelfEmployedLoanStrategy.class);
    private static final String EMPLOYMENT_STATUS = "SELF_EMPLOYED";

    @Override
    public LoanDecisionResponse evaluate(LoanApplicationRequest request) {
        log.info("Evaluating self-employed applicant: {} with credit score: {}",
                request.getApplicantId(), request.getCreditScore());

        if (request.getCreditScore() == null || request.getCreditScore() < 300) {
            return new LoanDecisionResponse(null, "REJECTED", null,
                    "Credit score too low or not provided for self-employed applicant");
        }

        if (request.getCreditScore() >= 750) {
            return new LoanDecisionResponse(null, "APPROVED", new BigDecimal("8.5"),
                    "Excellent credit profile for self-employed applicant");
        }

        if (request.getCreditScore() >= 600) {
            return new LoanDecisionResponse(null, "APPROVED", new BigDecimal("13.5"),
                    "Standard credit profile for self-employed applicant");
        }

        return new LoanDecisionResponse(null, "REJECTED", null,
                "Credit score below minimum threshold for self-employed applicant");
    }

    @Override
    public boolean supports(String employmentStatus) {
        return EMPLOYMENT_STATUS.equalsIgnoreCase(employmentStatus);
    }
}
