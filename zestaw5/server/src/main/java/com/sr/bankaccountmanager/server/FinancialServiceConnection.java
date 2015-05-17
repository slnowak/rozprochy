package com.sr.bankaccountmanager.server;

import FinancialNews.FinancialNewsReceiverPrx;
import FinancialNews.FinancialNewsReceiverPrxHelper;
import FinancialNews.FinancialNewsServerPrx;
import FinancialNews.FinancialNewsServerPrxHelper;
import Ice.Communicator;
import Ice.Identity;
import Ice.ObjectAdapter;
import com.sr.bankaccountmanager.news.domain.DomainFinancialNewsReceiver;
import com.sr.bankaccountmanager.news.domain.ExchangeRateRepository;
import com.sr.bankaccountmanager.news.domain.InterestRepository;
import com.sr.bankaccountmanager.news.infrastructure.FinancialServerPinger;

import java.util.UUID;

/**
 * Created by novy on 17.05.15.
 */

class FinancialServiceConnection {

    public static final String FINANCIAL_NEWS_PROXY = "FinancialNewsProxy";

    public static void establish(Communicator communicator, ObjectAdapter adapter, InterestRepository interestRepository, ExchangeRateRepository exchangeRateRepository) {
        final Ice.ObjectPrx financialNewsBase = communicator.propertyToProxy(FINANCIAL_NEWS_PROXY);
        final FinancialNewsServerPrx financialNewsServerPrx = FinancialNewsServerPrxHelper.checkedCast(financialNewsBase);

        final DomainFinancialNewsReceiver domainFinancialNewsReceiver = new DomainFinancialNewsReceiver(
                interestRepository, exchangeRateRepository
        );
        final Identity financialNewsReceiverIdentity = new Identity();
        financialNewsReceiverIdentity.name = UUID.randomUUID().toString();
        financialNewsReceiverIdentity.category = "";
        final FinancialNewsReceiverPrx financialNewsReceiverPrx =
                FinancialNewsReceiverPrxHelper.checkedCast(
                        adapter.add(domainFinancialNewsReceiver, financialNewsReceiverIdentity)
                );

        financialNewsServerPrx.registerForNews(financialNewsReceiverPrx);
        financialNewsServerPrx.ice_getConnection().setAdapter(adapter);

        keepAliveFinancialServiceConnection(financialNewsServerPrx);
    }

    private static void keepAliveFinancialServiceConnection(FinancialNewsServerPrx financialNewsServerPrx) {
        new Thread(
                new FinancialServerPinger(financialNewsServerPrx)
        ).start();
    }
}
