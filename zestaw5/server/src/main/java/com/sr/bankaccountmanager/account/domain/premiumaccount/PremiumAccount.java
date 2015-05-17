package com.sr.bankaccountmanager.account.domain.premiumaccount;

import Bank.*;
import Ice.Current;
import Ice.FloatHolder;
import Ice.IntHolder;
import com.sr.bankaccountmanager.account.domain.DomainAccount;

/**
 * Created by novy on 16.05.15.
 */
public class PremiumAccount extends _PremiumAccountDisp implements DomainAccount {

    private final DomainAccount account;
    private final transient LoanCalculationService loanCalculationService;

    public PremiumAccount(DomainAccount account, LoanCalculationService loanCalculationService) {
        this.account = account;
        this.loanCalculationService = loanCalculationService;
    }

    @Override
    public int getBalance(Current __current) {
        return account.getBalance();
    }

    @Override
    public String getAccountNumber(Current __current) {
        return account.getAccountNumber();
    }

    @Override
    public void transferMoney(String accountNumber, int amount, Current __current) throws IncorrectAccountNumber, IncorrectAmount {
        account.transferMoney(accountNumber, amount, __current);
    }

    @Override
    public Currency currency() {
        return account.currency();
    }

    @Override
    public void increaseBalance(int amount) {
        account.increaseBalance(amount);
    }

    @Override
    public void decreaseBalance(int amount) {
        account.decreaseBalance(amount);
    }

    @Override
    public void calculateLoan(int amount, Currency curr, int period, FloatHolder interestRate, IntHolder totalCost, Current __current) throws IncorrectData {
        final LoanCalculationData loanCalculationData = new LoanCalculationData(amount, currency(), curr, period);
        final LoanDetails loanDetails = loanCalculationService.calculateLoan(loanCalculationData);
        interestRate.value = loanDetails.getInterestRate();
        totalCost.value = loanDetails.getTotalCost();
    }
}
