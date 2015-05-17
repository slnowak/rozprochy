package com.sr.bankaccountmanager.news.domain;

import FinancialNews.Currency;
import FinancialNews._FinancialNewsReceiverDisp;
import Ice.Current;

/**
 * Created by novy on 16.05.15.
 */
public class DomainFinancialNewsReceiver extends _FinancialNewsReceiverDisp {

    private final InterestRepository interestRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    public DomainFinancialNewsReceiver(InterestRepository interestRepository, ExchangeRateRepository exchangeRateRepository) {
        this.interestRepository = interestRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public void interestRate(float rate, Currency curr, Current __current) {
        System.out.println("got new interest: " + curr + " " + rate);
        interestRepository.updateInterestRateFor(curr, rate);
    }

    @Override
    public void exchangeRate(float rate, Currency curr1, Currency curr2, Current __current) {
        System.out.println("got exchange: " + curr1 + " " + curr2 + " " + rate);
        exchangeRateRepository.updateExchangeRateFor(
                ExchangeSides.of(curr1, curr2), rate
        );
    }
}
