package com.sr.bankaccountmanager.account;

import Bank.*;
import Ice.Current;

import java.io.Serializable;

/**
 * Created by novy on 16.05.15.
 */
public class AccountImpl extends _AccountDisp implements Account, Serializable {

    private static final int DIVIDE_FACTOR = 100;

    private final PersonalData personalData;

    public AccountImpl(PersonalData personalData) {
        this.personalData = personalData;
    }

    @Override
    public int getBalance(Current __current) {
        return 0;
    }

    @Override
    public String getAccountNumber(Current __current) {
        return "accnumber";
    }

    @Override
    public void transferMoney(String accountNumber, int amount, Current __current) throws IncorrectAccountNumber, IncorrectAmount {

//        if (amount)

    }
}
