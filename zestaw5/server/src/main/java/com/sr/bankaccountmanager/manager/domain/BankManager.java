package com.sr.bankaccountmanager.manager.domain;

import Bank.*;
import Ice.Current;
import Ice.StringHolder;
import com.google.common.base.Strings;
import com.sr.bankaccountmanager.account.domain.AccountFactory;
import com.sr.bankaccountmanager.account.domain.AccountRepository;
import com.sr.bankaccountmanager.account.domain.DomainAccount;

import java.util.UUID;

/**
 * Created by novy on 16.05.15.
 */
public class BankManager extends _BankManagerDisp implements Bank.BankManager {

    private final AccountRepository repository;
    private final AccountFactory factory;

    public BankManager(AccountRepository repository, AccountFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public void createAccount(PersonalData data, accountType type, StringHolder accountID, Current __current) throws IncorrectData, RequestRejected {

        if (!personalDataValid(data)) {
            throw new IncorrectData();
        }

        final DomainAccount account = factory.createAccount(type, data);
        final String newAccountId = randomAccountId();

        repository.save(newAccountId, account);
        accountID.value = newAccountId;

    }

    private boolean personalDataValid(PersonalData data) {
        return !Strings.isNullOrEmpty(data.firstName) &&
                !Strings.isNullOrEmpty(data.lastName) &&
                !Strings.isNullOrEmpty(data.nationality) &&
                !Strings.isNullOrEmpty(data.nationalIDNumber);
    }

    private String randomAccountId() {
        return UUID.randomUUID().toString();
    }


    @Override
    public void removeAccount(String accountID, Current __current) throws IncorrectData, NoSuchAccount {
        if (Strings.isNullOrEmpty(accountID)) {
            throw new IncorrectData();
        }

        repository.remove(accountID);
    }
}
