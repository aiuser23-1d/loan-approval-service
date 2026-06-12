package com.bank.loans.dto;

import java.math.BigDecimal;

public class ApplicantProfileRequest {
    private String applicantId;
    private String fullName;
    private String panNumber;
    private String emailAddress;
    private String mobileNumber;
    private BigDecimal netWorth;
    private Integer dependents;

    public String getApplicantId() { return applicantId; }
    public void setApplicantId(String a) { this.applicantId = a; }
    public String getFullName() { return fullName; }
    public void setFullName(String f) { this.fullName = f; }
    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String p) { this.panNumber = p; }
    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String e) { this.emailAddress = e; }
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String m) { this.mobileNumber = m; }
    public BigDecimal getNetWorth() { return netWorth; }
    public void setNetWorth(BigDecimal n) { this.netWorth = n; }
    public Integer getDependents() { return dependents; }
    public void setDependents(Integer d) { this.dependents = d; }
}