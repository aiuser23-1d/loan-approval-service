package com.bank.loans.service;

import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Decision engine that orchestrates loan decision strategies.
 * Selects the appropriate strategy based on employment status and delegates evaluation.
 */
@Component
public class LoanDecisionEngine {

    private static final Logger log = LoggerFactory.getLogger(LoanDecisionEngine.class);
    private final List<LoanDecisionStrategy> strategies;

    public LoanDecisionEngine(List<LoanDecisionStrategy> strategies) {
        this.strategies = Objects.requireNonNull(strategies,
                "Strategies list cannot be null");
        if (strategies.isEmpty()) {
            throw new IllegalArgumentException("At least one strategy must be provided");
        }
        log.info("LoanDecisionEngine initialized with {} strategies", strategies.size());
    }

    /**
     * Evaluates a loan application by selecting and applying the appropriate strategy.
     *
     * @param request the loan application request
     * @return the decision response
     */
    public LoanDecisionResponse evaluate(LoanApplicationRequest request) {
        Objects.requireNonNull(request, "LoanApplicationRequest cannot be null");

        log.debug("Evaluating loan application for applicant: {} with employment status: {}",
                request.getApplicantId(), request.getEmploymentStatus());

        // Find first strategy that supports this employment status
        for (LoanDecisionStrategy strategy : strategies) {
            if (strategy.supports(request.getEmploymentStatus())) {
                log.debug("Selected strategy: {} for employment status: {}",
                        strategy.getClass().getSimpleName(), request.getEmploymentStatus());
                return strategy.evaluate(request);
            }
        }

        // This should not happen if DefaultLoanStrategy is properly registered
        throw new IllegalStateException(
                "No suitable strategy found for employment status: " + request.getEmploymentStatus());
    }
}
