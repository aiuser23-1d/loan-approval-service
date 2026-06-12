package com.bank.loans.dto;

import java.math.BigDecimal;

public class LoanAmendmentRequest {
    private Long applicationId;
    private BigDecimal newRequestedAmount;
    private String reason;

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long a) { this.applicationId = a; }
    public BigDecimal getNewRequestedAmount() { return newRequestedAmount; }
    public void setNewRequestedAmount(BigDecimal n) { this.newRequestedAmount = n; }
    public String getReason() { return reason; }
    public void setReason(String r) { this.reason = r; }
}