package com.sr.bankaccountmanager.account.evictor;

import Bank.Account;
import com.sr.bankaccountmanager.account.AccountRepository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

/**
 * Created by novy on 16.05.15.
 */

public class FileAccountRepository implements AccountRepository {

    @Override
    public void save(String accountId, Account account) {
        throw new NotImplementedException();

    }

    @Override
    public Optional<Account> load(String accountId) {
        throw new NotImplementedException();
    }
}
