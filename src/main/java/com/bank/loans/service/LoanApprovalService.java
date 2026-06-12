package com.bank.loans.service;

import com.bank.loans.domain.LoanApplication;
import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;
import com.bank.loans.repository.LoanApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LoanApprovalService {

    private static final Logger log = LoggerFactory.getLogger(LoanApprovalService.class);

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanDecisionEngine loanDecisionEngine;

    public LoanApprovalService(LoanApplicationRepository loanApplicationRepository,
                               LoanDecisionEngine loanDecisionEngine) {
        this.loanApplicationRepository = Objects.requireNonNull(loanApplicationRepository,
                "LoanApplicationRepository cannot be null");
        this.loanDecisionEngine = Objects.requireNonNull(loanDecisionEngine,
                "LoanDecisionEngine cannot be null");
    }

    public LoanDecisionResponse processApplication(LoanApplicationRequest request) {
        Objects.requireNonNull(request, "LoanApplicationRequest cannot be null");
        log.info("Processing loan for applicant: {}", request.getApplicantId());

        return loanDecisionEngine.evaluate(request);
    }

    public List<LoanApplication> getLoanApplicationsByApplicant(String applicantId) {
        return loanApplicationRepository.findByApplicantId(applicantId).stream()
                .sorted((app1, app2) -> app2.getCreatedAt().compareTo(app1.getCreatedAt()))
                .collect(Collectors.toList());
    }
}