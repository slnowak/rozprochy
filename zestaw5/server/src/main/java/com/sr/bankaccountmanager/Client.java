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

        final StringHolder firstAccountStringHolder = new StringHolder();
        final PersonalData firstPersonalData = new PersonalData("s", "s", "s", "s");
        bankManager.createAccount(firstPersonalData, accountType.SILVER, firstAccountStringHolder);

        final StringHolder secondAccountStringHolder = new StringHolder();
        final PersonalData secondPersonalData = new PersonalData("s", "s", "s", "s");
        bankManager.createAccount(secondPersonalData, accountType.SILVER, secondAccountStringHolder);

        final AccountPrx firstAccountPrfx = AccountPrxHelper.checkedCast(
                communicator.stringToProxy(
                        "accounts/" + firstAccountStringHolder.value + ":" +
                                communicator.getProperties().getProperty("Accounts.Proxy").split(":")[1]
                )
        );

        final AccountPrx secondAccountPrfx = AccountPrxHelper.checkedCast(
                communicator.stringToProxy(
                        "accounts/" + secondAccountStringHolder.value + ":" +
                                communicator.getProperties().getProperty("Accounts.Proxy").split(":")[1]
                )
        );

        System.out.println("\n\n------BEFORE MONEY TRANSFER------");
        System.out.println("first account number: " + firstAccountPrfx.getAccountNumber());
        System.out.println("first account balance: " + firstAccountPrfx.getBalance());
        System.out.println("second account number: " + secondAccountPrfx.getAccountNumber());
        System.out.println("second account balance " + secondAccountPrfx.getBalance());

        firstAccountPrfx.transferMoney(secondAccountPrfx.getAccountNumber(), 3000);

        System.out.println("\n\n------AFTER MONEY TRANSFER------");
        System.out.println("first account number: " + firstAccountPrfx.getAccountNumber());
        System.out.println("first account balance: " + firstAccountPrfx.getBalance());
        System.out.println("second account number: " + secondAccountPrfx.getAccountNumber());
        System.out.println("second account balance: " + secondAccountPrfx.getBalance());
    }

}

