package com.sr.bankaccountmanager.account.infrastructure;

import Bank.Account;
import Bank.NoSuchAccount;
import com.sr.bankaccountmanager.account.domain.AccountRepository;
import com.sr.bankaccountmanager.account.domain.ByAccountNumberQuery;
import com.sr.bankaccountmanager.account.domain.DomainAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * Created by novy on 16.05.15.
 */
public class InMemoryAccountRepository implements AccountRepository, ByAccountNumberQuery {

    final Map<String, DomainAccount> accounts = new HashMap<>();

    @Override
    public void save(String accountId, DomainAccount account) {
        accounts.put(accountId, account);
    }

    @Override
    public DomainAccount load(String accountId) {
        return accounts.get(accountId);
    }

    @Override
    public void remove(String accountId) throws NoSuchAccount {
        final Account removedAccount = accounts.remove(accountId);

        if (isNull(removedAccount)) {
            throw new NoSuchAccount();
        }
    }

    @Override
    public Optional<DomainAccount> getByAccountNumber(String accountNumber) {
        return accounts
                .values()
                .stream()
                .filter(domainAccount -> domainAccount.getAccountNumber().equals(accountNumber))
                .findAny();
    }
}
