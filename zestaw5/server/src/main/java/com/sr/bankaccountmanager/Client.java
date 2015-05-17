package com.sr.bankaccountmanager;

import Bank.*;
import Ice.FloatHolder;
import Ice.IntHolder;
import Ice.StringHolder;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by novy on 16.05.15.
 */

public class Client {
    public static void main(String[] args) throws Exception {
        final Map<accountType, String> proxies = ImmutableMap.of(
                accountType.SILVER, "SilverAccounts.Proxy",
                accountType.PREMIUM, "PremiumAccounts.Proxy"
        );
        final Map<accountType, String> categories = ImmutableMap.of(
                accountType.SILVER, "silverAccounts/",
                accountType.PREMIUM, "premiumAccounts/"
        );
        Ice.Communicator communicator = Ice.Util.initialize(args);

        Ice.ObjectPrx base = communicator.propertyToProxy("BankManager.Proxy");
        BankManagerPrx bankManager = BankManagerPrxHelper.checkedCast(base);

        final StringHolder firstAccountStringHolder = new StringHolder();
        final PersonalData firstPersonalData = new PersonalData("s", "s", "s", "s");
        bankManager.createAccount(firstPersonalData, accountType.SILVER, firstAccountStringHolder);

        final StringHolder secondAccountStringHolder = new StringHolder();
        final PersonalData secondPersonalData = new PersonalData("s", "s", "s", "s");
        bankManager.createAccount(secondPersonalData, accountType.PREMIUM, secondAccountStringHolder);

        final AccountPrx firstAccountPrx = AccountPrxHelper.checkedCast(
                communicator.stringToProxy(
                        categories.get(accountType.SILVER) + firstAccountStringHolder.value + ":" +
                                communicator.getProperties().getProperty(proxies.get(accountType.SILVER)).split(":")[1]
                )
        );

        final PremiumAccountPrx secondAccountPrx = PremiumAccountPrxHelper.checkedCast(
                communicator.stringToProxy(
                        categories.get(accountType.PREMIUM) + secondAccountStringHolder.value + ":" +
                                communicator.getProperties().getProperty(proxies.get(accountType.PREMIUM)).split(":")[1]
                )
        );

        System.out.println("\n\n------BEFORE MONEY TRANSFER------");
        System.out.println("first account number: " + firstAccountPrx.getAccountNumber());
        System.out.println("first account balance: " + firstAccountPrx.getBalance());
        System.out.println("second account number: " + secondAccountPrx.getAccountNumber());
        System.out.println("second account balance " + secondAccountPrx.getBalance());

        firstAccountPrx.transferMoney(secondAccountPrx.getAccountNumber(), 3000);

        System.out.println("\n\n------AFTER MONEY TRANSFER------");
        System.out.println("first account number: " + firstAccountPrx.getAccountNumber());
        System.out.println("first account balance: " + firstAccountPrx.getBalance());
        System.out.println("second account number: " + secondAccountPrx.getAccountNumber());
        System.out.println("second account balance: " + secondAccountPrx.getBalance());

        // calculate loan
        final FloatHolder interestRateHolder = new FloatHolder();
        final IntHolder totalLoanCostHolder = new IntHolder();
        secondAccountPrx.calculateLoan(15000, Currency.USD, 15, interestRateHolder, totalLoanCostHolder);

        System.out.println("interest: " + interestRateHolder.value);
        System.out.println("total cost: " + totalLoanCostHolder.value);
    }

}

