package com.sr.bankaccountmanager;

import Bank.*;
import Ice.StringHolder;

/**
 * Created by novy on 16.05.15.
 */

public class Client {
    public static void main(String[] args) throws Exception {
        Ice.Communicator communicator = Ice.Util.initialize(args);

        Ice.ObjectPrx base = communicator.propertyToProxy("BankManager.Proxy");

        BankManagerPrx bankManager = BankManagerPrxHelper.checkedCast(base);

        final StringHolder accountIdHolder = new StringHolder();
        final PersonalData personalData = new PersonalData("", "", "", "");
        bankManager.createAccount(personalData, accountType.SILVER, accountIdHolder);

        final AccountPrx accountPrx = AccountPrxHelper.checkedCast(
                communicator.propertyToProxy("Accounts.Proxy")
        );

        System.out.println(accountPrx.getAccountNumber());

    }

}

