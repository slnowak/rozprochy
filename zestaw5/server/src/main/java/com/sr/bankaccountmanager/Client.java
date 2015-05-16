package com.sr.bankaccountmanager;

import Bank.BankManagerPrx;
import Bank.BankManagerPrxHelper;

/**
 * Created by novy on 16.05.15.
 */

public class Client {
    public static void main(String[] args) throws Exception {
        Ice.Communicator communicator = Ice.Util.initialize(args);

        Ice.ObjectPrx base = communicator.propertyToProxy("BankManager.Proxy");

        BankManagerPrx bankManager = BankManagerPrxHelper.checkedCast(base);
        bankManager.removeAccount("elo");
    }

}

