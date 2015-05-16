package com.sr.bankaccountmanager.account.domain;

import Bank.IncorrectAccountNumber;
import Bank.IncorrectAmount;
import Bank.PersonalData;
import Bank._AccountDisp;
import Ice.Current;

/**
 * Created by novy on 16.05.15.
 */
public class SilverAccount extends _AccountDisp implements DomainAccount {

    private final String accountNumber;
    private final PersonalData personalData;
    private int balance;
    private final transient MoneyTransferService moneyTransferService;

    public SilverAccount(String accountNumber, PersonalData personalData, MoneyTransferService moneyTransferService) {
        this(accountNumber, personalData, 0, moneyTransferService);
    }

    public SilverAccount(String accountNumber, PersonalData personalData, int balance, MoneyTransferService moneyTransferService) {
        this.accountNumber = accountNumber;
        this.personalData = personalData;
        this.balance = balance;
        this.moneyTransferService = moneyTransferService;
    }

    @Override
    public int getBalance(Current __current) {
        return balance;
    }

    @Override
    public String getAccountNumber(Current __current) {
        return accountNumber;
    }

    @Override
    public void transferMoney(String accountNumber, int amount, Current __current) throws IncorrectAccountNumber, IncorrectAmount {

        final MoneyTransfer moneyTransfer = new MoneyTransfer(this.accountNumber, accountNumber, amount);
        moneyTransferService.makeTransfer(moneyTransfer);

    }

    @Override
    public void increaseBalance(int amount) {
        balance += amount;
    }

    @Override
    public void decreaseBalance(int amount) {
        balance -= amount;
    }
}
