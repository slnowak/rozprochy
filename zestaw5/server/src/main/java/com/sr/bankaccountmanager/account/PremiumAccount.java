package com.sr.bankaccountmanager.account;

import Bank.*;
import Ice.Current;
import Ice.FloatHolder;
import Ice.IntHolder;

/**
 * Created by novy on 16.05.15.
 */
public class PremiumAccount extends _PremiumAccountDisp implements Bank.PremiumAccount {

    @Override
    public void calculateLoan(int amount, Currency curr, int period, FloatHolder interestRate, IntHolder totalCost, Current __current) throws IncorrectData {

    }

    @Override
    public int getBalance(Current __current) {
        return 0;
    }

    @Override
    public String getAccountNumber(Current __current) {
        return null;
    }

    @Override
    public void transferMoney(String accountNumber, int amount, Current __current) throws IncorrectAccountNumber, IncorrectAmount {

    }
}
