package com.sr.bankaccountmanager;

import Ice.Identity;
import Ice.ServantLocator;
import com.sr.bankaccountmanager.account.domain.AccountFactory;
import com.sr.bankaccountmanager.account.domain.MoneyTransferService;
import com.sr.bankaccountmanager.account.infrastructure.InMemoryAccountRepository;
import com.sr.bankaccountmanager.account.infrastructure.evictor.AccountEvictor;
import com.sr.bankaccountmanager.account.infrastructure.FileAccountRepository;
import com.sr.bankaccountmanager.manager.domain.BankManager;

/**
 * Created by novy on 16.05.15.
 */
public class Server {

    public int run(String[] args) {
        Ice.Communicator communicator = Ice.Util.initialize(args);

        Ice.ObjectAdapter adapter = communicator.createObjectAdapter("ACCOUNTS");


        final InMemoryAccountRepository cache = new InMemoryAccountRepository();
        final FileAccountRepository repository = new FileAccountRepository();
        final MoneyTransferService moneyTransferService = new MoneyTransferService(cache);
        final AccountFactory accountFactory = new AccountFactory(moneyTransferService);

        final ServantLocator accountEvictor = new AccountEvictor(0, cache, repository);
        adapter.addServantLocator(accountEvictor, "accounts");

        Identity identity = communicator.stringToIdentity("managers/bankManager");
        final Bank.BankManager bankManagerServant = new BankManager(cache, accountFactory);
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
