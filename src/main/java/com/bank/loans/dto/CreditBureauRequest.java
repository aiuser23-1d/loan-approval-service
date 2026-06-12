package com.bank.loans.dto;

public class CreditBureauRequest {
    private String panNumber;
    private String bureauName;
    private String consentToken;

    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String p) { this.panNumber = p; }
    public String getBureauName() { return bureauName; }
    public void setBureauName(String b) { this.bureauName = b; }
    public String getConsentToken() { return consentToken; }
    public void setConsentToken(String c) { this.consentToken = c; }
}