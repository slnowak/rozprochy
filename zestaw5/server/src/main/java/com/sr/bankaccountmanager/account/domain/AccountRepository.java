package com.sr.bankaccountmanager.account.domain;

import Bank.NoSuchAccount;

/**
 * Created by novy on 16.05.15.
 */
public interface AccountRepository {

    void save(String accountId, DomainAccount account);

    DomainAccount load(String accountId);

    void remove(String accountId) throws NoSuchAccount;
}
