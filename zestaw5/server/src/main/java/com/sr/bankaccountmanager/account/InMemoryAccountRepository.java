package com.sr.bankaccountmanager.account;

import Bank.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by novy on 16.05.15.
 */
public class InMemoryAccountRepository implements AccountRepository {

    final Map<String, Account> accounts = new HashMap<>();

    @Override
    public void save(String accountId, Account account) {
        accounts.put(accountId, account);
    }

    @Override
    public Optional<Account> load(String accountId) {
        return Optional.ofNullable(
                accounts.get(accountId)
        );
    }
}
