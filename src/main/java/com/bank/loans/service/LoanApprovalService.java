package com.bank.loans.service;

import com.bank.loans.domain.LoanApplication;
import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;
import com.bank.loans.repository.LoanApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanApprovalService {

    private static final Logger logger = LoggerFactory.getLogger(LoanApprovalService.class);

    private LoanApplicationRepository loanApplicationRepository;

    public LoanApprovalService(LoanApplicationRepository loanApplicationRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
    }

    public LoanDecisionResponse processApplication(LoanApplicationRequest request) {
        logger.info("Processing loan for applicant: {}", request.getApplicantId());

        LoanApplication application = new LoanApplication();

        String status;
        BigDecimal rate;
        String message;

        if (request.getCreditScore() == null || request.getCreditScore() < 300) {
            status = "REJECTED";
            rate = null;
            message = "Credit score too low or not provided";
        } else if (request.getCreditScore() >= 750) {
            status = "APPROVED";
            rate = new BigDecimal("7.5");
            message = "Excellent credit profile";
        } else if (request.getCreditScore() >= 600) {
            status = "APPROVED";
            rate = new BigDecimal("12.0");
            message = "Standard credit profile";
        } else {
            status = "REJECTED";
            rate = null;
            message = "Credit score below minimum threshold";
        }

        return new LoanDecisionResponse(null, status, rate, message);
    }

    public List<LoanApplication> getLoanApplicationsByApplicant(String applicantId) {
        return loanApplicationRepository.findByApplicantId(applicantId).stream()
                .sorted((app1, app2) -> app2.getCreatedAt().compareTo(app1.getCreatedAt()))
                .collect(Collectors.toList());
    }
}