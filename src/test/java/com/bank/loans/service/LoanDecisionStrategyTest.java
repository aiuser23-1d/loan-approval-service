package com.bank.loans.service;

import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;
import com.bank.loans.service.strategies.DefaultLoanStrategy;
import com.bank.loans.service.strategies.SalariedLoanStrategy;
import com.bank.loans.service.strategies.SelfEmployedLoanStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Loan Decision Strategy Tests")
class LoanDecisionStrategyTest {

    private LoanApplicationRequest request;

    @BeforeEach
    void setUp() {
        request = new LoanApplicationRequest();
        request.setApplicantId("APP123");
    }

    // ========== SalariedLoanStrategy Tests ==========

    @Test
    @DisplayName("SalariedLoanStrategy: Should approve with 7.5% rate when credit score >= 750")
    void testSalariedStrategy_ApprovedWithExcellentScore() {
        // Arrange
        SalariedLoanStrategy strategy = new SalariedLoanStrategy();
        request.setCreditScore(780);
        request.setEmploymentStatus("SALARIED");
        request.setRequestedAmount(new BigDecimal("500000"));

        // Act
        LoanDecisionResponse response = strategy.evaluate(request);

        // Assert
        assertNotNull(response);
        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("7.5"), response.getInterestRate());
        assertTrue(response.getMessage().contains("Excellent credit profile"));
    }

    @Test
    @DisplayName("SalariedLoanStrategy: Should approve with 12.0% rate when credit score 600-749")
    void testSalariedStrategy_ApprovedWithStandardScore() {
        // Arrange
        SalariedLoanStrategy strategy = new SalariedLoanStrategy();
        request.setCreditScore(650);
        request.setEmploymentStatus("SALARIED");
        request.setRequestedAmount(new BigDecimal("500000"));

        // Act
        LoanDecisionResponse response = strategy.evaluate(request);

        // Assert
        assertNotNull(response);
        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("12.0"), response.getInterestRate());
        assertTrue(response.getMessage().contains("Standard credit profile"));
    }

    @Test
    @DisplayName("SalariedLoanStrategy: Should reject when credit score < 600")
    void testSalariedStrategy_RejectedWithLowScore() {
        // Arrange
        SalariedLoanStrategy strategy = new SalariedLoanStrategy();
        request.setCreditScore(550);
        request.setEmploymentStatus("SALARIED");
        request.setRequestedAmount(new BigDecimal("500000"));

        // Act
        LoanDecisionResponse response = strategy.evaluate(request);

        // Assert
        assertNotNull(response);
        assertEquals("REJECTED", response.getStatus());
        assertNull(response.getInterestRate());
        assertTrue(response.getMessage().contains("minimum threshold"));
    }

    @Test
    @DisplayName("SalariedLoanStrategy: Should reject when credit score is null")
    void testSalariedStrategy_RejectedWithNullScore() {
        // Arrange
        SalariedLoanStrategy strategy = new SalariedLoanStrategy();
        request.setCreditScore(null);
        request.setEmploymentStatus("SALARIED");
        request.setRequestedAmount(new BigDecimal("500000"));

        // Act
        LoanDecisionResponse response = strategy.evaluate(request);

        // Assert
        assertNotNull(response);
        assertEquals("REJECTED", response.getStatus());
        assertNull(response.getInterestRate());
        assertTrue(response.getMessage().contains("not provided"));
    }

    @Test
    @DisplayName("SalariedLoanStrategy: Should support SALARIED employment status")
    void testSalariedStrategy_SupportsCorrectStatus() {
        // Arrange
        SalariedLoanStrategy strategy = new SalariedLoanStrategy();

        // Act & Assert
        assertTrue(strategy.supports("SALARIED"));
        assertTrue(strategy.supports("salaried"));
        assertFalse(strategy.supports("SELF_EMPLOYED"));
        assertFalse(strategy.supports("UNEMPLOYED"));
    }

    // ========== SelfEmployedLoanStrategy Tests ==========

    @Test
    @DisplayName("SelfEmployedLoanStrategy: Should approve with 8.5% rate when credit score >= 750")
    void testSelfEmployedStrategy_ApprovedWithExcellentScore() {
        // Arrange
        SelfEmployedLoanStrategy strategy = new SelfEmployedLoanStrategy();
        request.setCreditScore(780);
        request.setEmploymentStatus("SELF_EMPLOYED");
        request.setRequestedAmount(new BigDecimal("500000"));

        // Act
        LoanDecisionResponse response = strategy.evaluate(request);

        // Assert
        assertNotNull(response);
        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("8.5"), response.getInterestRate());
        assertTrue(response.getMessage().contains("Excellent credit profile"));
    }

    @Test
    @DisplayName("SelfEmployedLoanStrategy: Should approve with 13.5% rate when credit score 600-749")
    void testSelfEmployedStrategy_ApprovedWithStandardScore() {
        // Arrange
        SelfEmployedLoanStrategy strategy = new SelfEmployedLoanStrategy();
        request.setCreditScore(680);
        request.setEmploymentStatus("SELF_EMPLOYED");
        request.setRequestedAmount(new BigDecimal("500000"));

        // Act
        LoanDecisionResponse response = strategy.evaluate(request);

        // Assert
        assertNotNull(response);
        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("13.5"), response.getInterestRate());
        assertTrue(response.getMessage().contains("Standard credit profile"));
    }

    @Test
    @DisplayName("SelfEmployedLoanStrategy: Should reject when credit score < 600")
    void testSelfEmployedStrategy_RejectedWithLowScore() {
        // Arrange
        SelfEmployedLoanStrategy strategy = new SelfEmployedLoanStrategy();
        request.setCreditScore(580);
        request.setEmploymentStatus("SELF_EMPLOYED");
        request.setRequestedAmount(new BigDecimal("500000"));

        // Act
        LoanDecisionResponse response = strategy.evaluate(request);

        // Assert
        assertNotNull(response);
        assertEquals("REJECTED", response.getStatus());
        assertNull(response.getInterestRate());
        assertTrue(response.getMessage().contains("minimum threshold"));
    }

    @Test
    @DisplayName("SelfEmployedLoanStrategy: Should support SELF_EMPLOYED employment status")
    void testSelfEmployedStrategy_SupportsCorrectStatus() {
        // Arrange
        SelfEmployedLoanStrategy strategy = new SelfEmployedLoanStrategy();

        // Act & Assert
        assertTrue(strategy.supports("SELF_EMPLOYED"));
        assertTrue(strategy.supports("self_employed"));
        assertFalse(strategy.supports("SALARIED"));
        assertFalse(strategy.supports("UNEMPLOYED"));
    }

    // ========== DefaultLoanStrategy Tests ==========

    @Test
    @DisplayName("DefaultLoanStrategy: Should approve with 15.0% rate when credit score >= 750")
    void testDefaultStrategy_ApprovedWithExcellentScore() {
        // Arrange
        DefaultLoanStrategy strategy = new DefaultLoanStrategy();
        request.setCreditScore(800);
        request.setEmploymentStatus("RETIRED");
        request.setRequestedAmount(new BigDecimal("500000"));

        // Act
        LoanDecisionResponse response = strategy.evaluate(request);

        // Assert
        assertNotNull(response);
        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("15.0"), response.getInterestRate());
        assertTrue(response.getMessage().contains("premium rate"));
    }

    @Test
    @DisplayName("DefaultLoanStrategy: Should reject when credit score < 750")
    void testDefaultStrategy_RejectedWithLowScore() {
        // Arrange
        DefaultLoanStrategy strategy = new DefaultLoanStrategy();
        request.setCreditScore(700);
        request.setEmploymentStatus("UNEMPLOYED");
        request.setRequestedAmount(new BigDecimal("500000"));

        // Act
        LoanDecisionResponse response = strategy.evaluate(request);

        // Assert
        assertNotNull(response);
        assertEquals("REJECTED", response.getStatus());
        assertNull(response.getInterestRate());
        assertTrue(response.getMessage().contains("excellent credit (750+)"));
    }

    @Test
    @DisplayName("DefaultLoanStrategy: Should support any employment status (catch-all)")
    void testDefaultStrategy_SupportsAllStatus() {
        // Arrange
        DefaultLoanStrategy strategy = new DefaultLoanStrategy();

        // Act & Assert
        assertFalse(strategy.supports("SALARIED"));
        assertFalse(strategy.supports("SELF_EMPLOYED"));
        assertTrue(strategy.supports("UNEMPLOYED"));
        assertTrue(strategy.supports("RETIRED"));
        assertTrue(strategy.supports("ANYTHING"));
        assertTrue(strategy.supports(null));
    }

    // ========== LoanDecisionEngine Tests ==========

    @Test
    @DisplayName("LoanDecisionEngine: Should select SalariedLoanStrategy for SALARIED status")
    void testEngine_SelectsSalariedStrategy() {
        // Arrange
        LoanDecisionEngine engine = new LoanDecisionEngine(java.util.List.of(
                new SalariedLoanStrategy(),
                new SelfEmployedLoanStrategy(),
                new DefaultLoanStrategy()
        ));
        request.setCreditScore(750);
        request.setEmploymentStatus("SALARIED");
        request.setRequestedAmount(new BigDecimal("500000"));

        // Act
        LoanDecisionResponse response = engine.evaluate(request);

        // Assert
        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("7.5"), response.getInterestRate()); // Salaried rate
    }

    @Test
    @DisplayName("LoanDecisionEngine: Should select SelfEmployedLoanStrategy for SELF_EMPLOYED status")
    void testEngine_SelectsSelfEmployedStrategy() {
        // Arrange
        LoanDecisionEngine engine = new LoanDecisionEngine(java.util.List.of(
                new SalariedLoanStrategy(),
                new SelfEmployedLoanStrategy(),
                new DefaultLoanStrategy()
        ));
        request.setCreditScore(750);
        request.setEmploymentStatus("SELF_EMPLOYED");
        request.setRequestedAmount(new BigDecimal("500000"));

        // Act
        LoanDecisionResponse response = engine.evaluate(request);

        // Assert
        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("8.5"), response.getInterestRate()); // Self-employed rate
    }

    @Test
    @DisplayName("LoanDecisionEngine: Should select DefaultLoanStrategy for unrecognized status")
    void testEngine_SelectsDefaultStrategy() {
        // Arrange
        LoanDecisionEngine engine = new LoanDecisionEngine(java.util.List.of(
                new SalariedLoanStrategy(),
                new SelfEmployedLoanStrategy(),
                new DefaultLoanStrategy()
        ));
        request.setCreditScore(800);
        request.setEmploymentStatus("UNKNOWN_STATUS");
        request.setRequestedAmount(new BigDecimal("500000"));

        // Act
        LoanDecisionResponse response = engine.evaluate(request);

        // Assert
        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("15.0"), response.getInterestRate()); // Default rate
    }

    @Test
    @DisplayName("LoanDecisionEngine: Should throw NullPointerException when request is null")
    void testEngine_ThrowsExceptionForNullRequest() {
        // Arrange
        LoanDecisionEngine engine = new LoanDecisionEngine(java.util.List.of(
                new DefaultLoanStrategy()
        ));

        // Act & Assert
        assertThrows(NullPointerException.class, () -> engine.evaluate(null));
    }

    @Test
    @DisplayName("LoanDecisionEngine: Should throw IllegalArgumentException when strategies list is empty")
    void testEngine_ThrowsExceptionForEmptyStrategiesList() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new LoanDecisionEngine(java.util.List.of())
        );
    }
}
