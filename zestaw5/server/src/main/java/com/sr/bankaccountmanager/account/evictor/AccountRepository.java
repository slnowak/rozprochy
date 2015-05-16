package com.sr.bankaccountmanager.account.evictor;

import Bank.Account;

/**
 * Created by novy on 16.05.15.
 */
public interface AccountRepository {

    void save(Account account);
    Account load(String accountId);
}
