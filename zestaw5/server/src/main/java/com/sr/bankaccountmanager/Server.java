package com.sr.bankaccountmanager;

import Bank.*;
import Ice.Identity;

/**
 * Created by novy on 16.05.15.
 */
public class Server {

    public int run(String[] args) {
        Ice.Communicator communicator = Ice.Util.initialize(args);

        Ice.ObjectAdapter adapter = communicator.createObjectAdapter("SR");

        Identity identity = communicator.stringToIdentity("managers/bankManager");
        final BankManager bankManagerServant = new BankManagerImpl();

        adapter.add(bankManagerServant, identity);

        adapter.activate();

        System.out.println("Entering event processing loop...");
        communicator.waitForShutdown();

        return 0;
    }

    public static void
    main(String[] args) {
        Server app = new Server();
        int status = app.run(args);
        System.exit(status);
    }
}
