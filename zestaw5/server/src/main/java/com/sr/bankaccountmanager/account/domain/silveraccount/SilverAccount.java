package com.sr.bankaccountmanager.account.domain.silveraccount;

import Bank.*;
import Ice.Current;
import com.sr.bankaccountmanager.account.domain.DomainAccount;

/**
 * Created by novy on 16.05.15.
 */
public class SilverAccount extends _AccountDisp implements DomainAccount {

    private final String accountNumber;
    private final PersonalData personalData;
    private int balance;
    private final Currency currency;
    private final transient MoneyTransferService moneyTransferService;

    public SilverAccount(String accountNumber, PersonalData personalData, MoneyTransferService moneyTransferService) {
        this(accountNumber, personalData, 0, Currency.PLN, moneyTransferService);
    }

    public SilverAccount(String accountNumber, PersonalData personalData, int balance, Currency currency, MoneyTransferService moneyTransferService) {
        this.accountNumber = accountNumber;
        this.personalData = personalData;
        this.balance = balance;
        this.currency = currency;
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

        // could be done via __current.adapter.findServantLocator("category") for silver
        // and adapter.find(identity) for premium, but this is kind of ugly

        final MoneyTransfer moneyTransfer = new MoneyTransfer(this.accountNumber, accountNumber, amount);
        moneyTransferService.makeTransfer(moneyTransfer);

    }

    @Override
    public Currency currency() {
        return currency;
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
