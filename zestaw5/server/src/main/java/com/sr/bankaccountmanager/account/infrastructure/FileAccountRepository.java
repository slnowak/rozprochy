package com.sr.bankaccountmanager.account.infrastructure;

import Bank.NoSuchAccount;
import com.google.common.io.Files;
import com.sr.bankaccountmanager.account.domain.AccountRepository;
import com.sr.bankaccountmanager.account.domain.DomainAccount;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.IOException;

/**
 * Created by novy on 16.05.15.
 */

public class FileAccountRepository implements AccountRepository {

    private static final String PATH_PREFIX = "data/accounts/";

    @Override
    public void save(String accountId, DomainAccount account) {
        final byte[] serializedAccount = SerializationUtils.serialize(account);
        try {
            Files.write(serializedAccount, new File(makeFilePath(accountId)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public DomainAccount load(String accountId) {
        try {
            final byte[] serializedAccount = FileUtils.readFileToByteArray(
                    new File(makeFilePath(accountId))
            );
            return SerializationUtils.deserialize(serializedAccount);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void remove(String accountId) throws NoSuchAccount {

        final File accountFile = new File(makeFilePath(accountId));
        if (accountFile.exists()) {
            accountFile.delete();
        } else {
            throw new NoSuchAccount();
        }
    }

    private String makeFilePath(String accountId) {
        return PATH_PREFIX + accountId;
    }
}
