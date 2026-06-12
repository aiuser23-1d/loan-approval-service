package com.bank.loans.service;

import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;

/**
 * Strategy interface for loan decision evaluation.
 * Different implementations handle different employment statuses and decision criteria.
 */
public interface LoanDecisionStrategy {

    /**
     * Evaluates a loan application and returns a decision with interest rate.
     *
     * @param request the loan application request
     * @return the decision response containing status, interest rate, and message
     */
    LoanDecisionResponse evaluate(LoanApplicationRequest request);

    /**
     * Determines if this strategy supports the given employment status.
     *
     * @param employmentStatus the employment status to check
     * @return true if this strategy handles the given status, false otherwise
     */
    boolean supports(String employmentStatus);
}
