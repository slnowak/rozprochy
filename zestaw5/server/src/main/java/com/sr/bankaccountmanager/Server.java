package com.sr.bankaccountmanager;

import Bank.BankManager;
import Ice.Identity;
import Ice.ServantLocator;
import com.sr.bankaccountmanager.account.InMemoryAccountRepository;
import com.sr.bankaccountmanager.account.evictor.AccountEvictor;
import com.sr.bankaccountmanager.account.evictor.FileAccountRepository;

/**
 * Created by novy on 16.05.15.
 */
public class Server {

    public int run(String[] args) {
        Ice.Communicator communicator = Ice.Util.initialize(args);

        Ice.ObjectAdapter adapter = communicator.createObjectAdapter("ACCOUNTS");


        final InMemoryAccountRepository cache = new InMemoryAccountRepository();
        final ServantLocator accountEvictor = new AccountEvictor(2, cache, new FileAccountRepository());
        adapter.addServantLocator(accountEvictor, "accounts");

        Identity identity = communicator.stringToIdentity("managers/bankManager");
        final BankManager bankManagerServant = new BankManagerImpl(cache);
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
