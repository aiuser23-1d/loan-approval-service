package com.bank.loans.service.strategies;

import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;
import com.bank.loans.service.LoanDecisionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Default strategy for evaluating loan applications from unemployed, retired,
 * or other unrecognized employment statuses.
 */
@Service
public class DefaultLoanStrategy implements LoanDecisionStrategy {

    private static final Logger log = LoggerFactory.getLogger(DefaultLoanStrategy.class);

    @Override
    public LoanDecisionResponse evaluate(LoanApplicationRequest request) {
        log.warn("Applying default strategy for applicant: {} with employment status: {}",
                request.getApplicantId(), request.getEmploymentStatus());

        // Default strategy applies conservative rules for unknown employment types
        if (request.getCreditScore() == null || request.getCreditScore() < 750) {
            return new LoanDecisionResponse(null, "REJECTED", null,
                    "Loan applications for this employment status require excellent credit (750+)");
        }

        return new LoanDecisionResponse(null, "APPROVED", new BigDecimal("15.0"),
                "Approved with premium rate for non-standard employment status");
    }

    @Override
    public boolean supports(String employmentStatus) {
        // Default strategy supports unemployed, retired, and unrecognized statuses
        if (employmentStatus == null) {
            return true;
        }
        String normalized = employmentStatus.toUpperCase();
        return normalized.equals("UNEMPLOYED") || 
               normalized.equals("RETIRED") || 
               !isSupportedBySpecificStrategy(normalized);
    }

    private boolean isSupportedBySpecificStrategy(String status) {
        // Check if this status is handled by other specific strategies
        return status.equals("SALARIED") || status.equals("SELF_EMPLOYED");
    }
}
