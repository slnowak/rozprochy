package com.sr.bankaccountmanager;

import Bank.*;
import Ice.Current;
import Ice.StringHolder;
import com.sr.bankaccountmanager.account.AccountImpl;

import java.util.UUID;

/**
 * Created by novy on 16.05.15.
 */
public class BankManagerImpl extends _BankManagerDisp implements Bank.BankManager {

    @Override
    public void createAccount(PersonalData data, accountType type, StringHolder accountID, Current __current) throws IncorrectData, RequestRejected {

        if (type == accountType.SILVER) {
            final String generatedAccountId = createSilverAccount(data);
            accountID.value = generatedAccountId;
        } else if (type == accountType.PREMIUM) {
            // todo: no elo
        } else {
            throw new IncorrectData();
        }

    }

    private String createSilverAccount(PersonalData data) {
        final Account account = new AccountImpl(data);
        return UUID.randomUUID().toString();
    }

    @Override
    public void removeAccount(String accountID, Current __current) throws IncorrectData, NoSuchAccount {
        System.out.println("test");
    }
}
