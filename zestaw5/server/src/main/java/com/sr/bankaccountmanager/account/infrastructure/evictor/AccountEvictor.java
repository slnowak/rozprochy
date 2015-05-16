package com.sr.bankaccountmanager.account.infrastructure.evictor;

import Ice.Current;
import Ice.LocalObjectHolder;
import com.sr.bankaccountmanager.account.domain.AccountRepository;
import com.sr.bankaccountmanager.account.domain.DomainAccount;
import com.sr.bankaccountmanager.account.infrastructure.evictor.baseevictor.EvictorBase;
import org.apache.commons.lang3.ObjectUtils;

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

        final String accountId = current.id.name;
        cookie.value = accountId;

        return ObjectUtils.firstNonNull(
                cache.load(accountId), repository.load(accountId)
        );
    }

    @Override
    public void evict(Ice.Object servant, java.lang.Object cookie) {
        repository.save(
                (String) cookie, (DomainAccount) servant
        );
    }
}
