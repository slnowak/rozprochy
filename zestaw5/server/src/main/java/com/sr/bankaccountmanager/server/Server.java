package com.sr.bankaccountmanager.server;

import Ice.Identity;
import Ice.ServantLocator;
import com.sr.bankaccountmanager.account.domain.AccountFactory;
import com.sr.bankaccountmanager.account.domain.premiumaccount.LoanCalculationService;
import com.sr.bankaccountmanager.account.domain.silveraccount.MoneyTransferService;
import com.sr.bankaccountmanager.account.infrastructure.FileAccountRepository;
import com.sr.bankaccountmanager.account.infrastructure.InMemoryAccountRepository;
import com.sr.bankaccountmanager.account.infrastructure.evictor.AccountEvictor;
import com.sr.bankaccountmanager.manager.domain.BankManager;
import com.sr.bankaccountmanager.news.infrastructure.InMemoryExchangeRateRepository;
import com.sr.bankaccountmanager.news.infrastructure.InterestRepository;

/**
 * Created by novy on 16.05.15.
 */
public class Server {

    public int run(String[] args) {
        Ice.Communicator communicator = Ice.Util.initialize(args);
        Ice.ObjectAdapter adapter = communicator.createObjectAdapter("BANKSERVICE");

        //init deps
        final InMemoryAccountRepository cache = new InMemoryAccountRepository();
        final FileAccountRepository repository = new FileAccountRepository();
        final MoneyTransferService moneyTransferService = new MoneyTransferService(cache);

        final InterestRepository interestRepository = new InterestRepository();
        final InMemoryExchangeRateRepository inMemoryExchangeRateRepository = new InMemoryExchangeRateRepository();
        final LoanCalculationService loanCalculationService =
                new LoanCalculationService(interestRepository, inMemoryExchangeRateRepository);
        final AccountFactory accountFactory = new AccountFactory(moneyTransferService, loanCalculationService);

        // register evictor for premium accounts
        final ServantLocator accountEvictor = new AccountEvictor(0, cache, repository);
        adapter.addServantLocator(accountEvictor, "silverAccounts");

        // register bankmanager servant
        Identity identity = communicator.stringToIdentity("managers/bankManager");
        final Bank.BankManager bankManagerServant = new BankManager(cache, repository, accountFactory);
        adapter.add(bankManagerServant, identity);

        // two-way connection with remote financial service
        FinancialServiceConnection.establish(
                communicator, adapter, interestRepository, inMemoryExchangeRateRepository
        );

        adapter.activate();

        System.out.println("Entering event processing loop...");
        communicator.waitForShutdown();


        return 0;
    }


    public static void main(String[] args) {
        Server app = new Server();
        int status = app.run(args);
        System.exit(status);
    }
}
