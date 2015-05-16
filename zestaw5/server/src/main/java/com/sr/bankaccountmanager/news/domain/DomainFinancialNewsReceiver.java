package com.sr.bankaccountmanager.news.domain;

import FinancialNews.Currency;
import FinancialNews._FinancialNewsReceiverDisp;
import Ice.Current;

/**
 * Created by novy on 16.05.15.
 */
public class DomainFinancialNewsReceiver extends _FinancialNewsReceiverDisp {

    public DomainFinancialNewsReceiver() {
    }

    @Override
    public void interestRate(float rate, Currency curr, Current __current) {
        System.out.println("interest changed " + rate + " " + curr);

    }

    @Override
    public void exchangeRate(float rate, Currency curr1, Currency curr2, Current __current) {
        System.out.println("exchange rate changed! " + rate + " " + curr1 + " " + curr2);
    }
}
