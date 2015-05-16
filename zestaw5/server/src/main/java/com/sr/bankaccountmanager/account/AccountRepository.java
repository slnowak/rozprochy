package com.sr.bankaccountmanager.account;

import Bank.Account;

import java.util.Optional;

/**
 * Created by novy on 16.05.15.
 */
public interface AccountRepository {

    void save(String accountId, Account account);
    Optional<Account> load(String accountId);
}
