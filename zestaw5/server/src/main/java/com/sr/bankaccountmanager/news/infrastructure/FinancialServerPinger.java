package com.sr.bankaccountmanager.news.infrastructure;

import FinancialNews.FinancialNewsServerPrx;

import java.util.concurrent.TimeUnit;

/**
 * Created by novy on 17.05.15.
 */
public class FinancialServerPinger implements Runnable {

    private static final int SLEEP_INTERVAL = 10;
    private final FinancialNewsServerPrx serverPrx;

    public FinancialServerPinger(FinancialNewsServerPrx serverPrx) {
        this.serverPrx = serverPrx;
    }

    @Override
    public void run() {

        while (true) {
            serverPrx.ice_ping();
            try {
                TimeUnit.SECONDS.sleep(SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
