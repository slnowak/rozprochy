package com.sr.bankaccountmanager.account;

import Bank.Account;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * Created by novy on 16.05.15.
 */

public class FileAccountRepository implements AccountRepository {

    private static final String PATH_PREFIX = "data/accounts/";

    @Override
    public void save(String accountId, Account account) {
//        todo: serializable cast hack
        final byte[] serializedAccount = SerializationUtils.serialize((Serializable) account);
        try {
            Files.write(serializedAccount, new File(makeFilePath(accountId)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Optional<Account> load(String accountId) {
        try {
            final byte[] serializedAccount = FileUtils.readFileToByteArray(
                    new File(makeFilePath(accountId))
            );
            return Optional.of(SerializationUtils.deserialize(serializedAccount));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private String makeFilePath(String accountId) {
        return PATH_PREFIX + accountId;
    }
}
