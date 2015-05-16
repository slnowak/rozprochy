package com.sr.bankaccountmanager.account.domain;

import Bank.*;
import Ice.Current;
import Ice.FloatHolder;
import Ice.IntHolder;

/**
 * Created by novy on 16.05.15.
 */
public class PremiumAccount extends _PremiumAccountDisp implements DomainAccount {

    private final DomainAccount account;

    public PremiumAccount(DomainAccount account) {
        this.account = account;
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
    public void increaseBalance(int amount) {
        account.increaseBalance(amount);
    }

    @Override
    public void decreaseBalance(int amount) {
        account.decreaseBalance(amount);
    }

    @Override
    public void calculateLoan(int amount, Currency curr, int period, FloatHolder interestRate, IntHolder totalCost, Current __current) throws IncorrectData {

    }
}
