package com.sr.bankaccountmanager.account.infrastructure.evictor;

import Bank.Account;
import Ice.Current;
import Ice.LocalObjectHolder;
import com.sr.bankaccountmanager.account.domain.AccountRepository;
import com.sr.bankaccountmanager.account.domain.DomainAccount;
import com.sr.bankaccountmanager.account.infrastructure.evictor.baseevictor.EvictorBase;

import java.util.Optional;

/**
 * Created by novy on 16.05.15.
 */
public class AccountEvictor extends EvictorBase implements Ice.ServantLocator {

    private final AccountRepository cache;
    private final AccountRepository repository;

    public AccountEvictor(int size, AccountRepository cache, AccountRepository repository) {
        super(size);
        this.cache = cache;
        this.repository = repository;
    }

    @Override
    public Ice.Object add(Current current, LocalObjectHolder cookie) {
        Account account = null;

        // todo: refactor
        final String accountId = current.id.name;
        cookie.value = accountId;
        System.out.println(accountId);
        final Optional<DomainAccount> possibleCachedAccount = cache.load(accountId);
        if (possibleCachedAccount.isPresent()) {
            account = possibleCachedAccount.get();
        } else {
            final Optional<DomainAccount> possiblePersistentAccount = repository.load(accountId);
            if (possibleCachedAccount.isPresent()) {
                account = possiblePersistentAccount.get();
            }
        }

        return account;
    }

    @Override
    public void evict(Ice.Object servant, java.lang.Object cookie) {
        repository.save(
                (String) cookie, (DomainAccount) servant
        );
    }
}
