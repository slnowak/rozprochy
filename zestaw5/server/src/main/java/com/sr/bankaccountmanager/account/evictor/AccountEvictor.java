package com.sr.bankaccountmanager.account.evictor;

import Ice.Current;
import Ice.LocalObjectHolder;
import Ice.UserException;

/**
 * Created by novy on 16.05.15.
 */
public class AccountEvictor implements Ice.ServantLocator {

    private final int size;
    private final AccountRepository accountRepository;

    public AccountEvictor(int size, AccountRepository accountRepository) {
        this.size = size;
        this.accountRepository = accountRepository;
    }

    @Override
    public Ice.Object locate(Current curr, LocalObjectHolder cookie) throws UserException {
        return null;
    }

    @Override
    public void finished(Current curr, Ice.Object servant, java.lang.Object cookie) throws UserException {

    }

    @Override
    public void deactivate(String category) {

    }
}
