package com.sr.bankaccountmanager;

import Bank.*;
import Ice.Current;
import Ice.StringHolder;
import com.sr.bankaccountmanager.account.AccountImpl;
import com.sr.bankaccountmanager.account.AccountRepository;

import java.util.UUID;

/**
 * Created by novy on 16.05.15.
 */
public class BankManagerImpl extends _BankManagerDisp implements Bank.BankManager {

    private final AccountRepository repository;

    public BankManagerImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createAccount(PersonalData data, accountType type, StringHolder accountID, Current __current) throws IncorrectData, RequestRejected {

        final String accountId = randomAccountId();
        if (type == accountType.SILVER) {
            final Account silverAccount = createSilverAccount(data);
            repository.save(accountId, silverAccount);
            accountID.value = accountId;
        } else if (type == accountType.PREMIUM) {
            // todo: no elo
        } else {
            throw new IncorrectData();
        }

    }

    private String randomAccountId() {
        return UUID.randomUUID().toString();
    }

    private Account createSilverAccount(PersonalData data) {
        return new AccountImpl(data);
    }

    @Override
    public void removeAccount(String accountID, Current __current) throws IncorrectData, NoSuchAccount {
        System.out.println("test");
    }
}
