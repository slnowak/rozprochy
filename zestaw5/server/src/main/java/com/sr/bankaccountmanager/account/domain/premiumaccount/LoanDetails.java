package com.sr.bankaccountmanager.account.domain.premiumaccount;

/**
 * Created by novy on 16.05.15.
 */
public class LoanDetails {

    private final Float interestRate;
    private final int totalCost;

    public LoanDetails(Float interestRate, int totalCost) {
        this.interestRate = interestRate;
        this.totalCost = totalCost;
    }

    public Float getInterestRate() {
        return interestRate;
    }

    public int getTotalCost() {
        return totalCost;
    }
}
