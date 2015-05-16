package com.sr.bankaccountmanager.news.domain;

/**
 * Created by novy on 16.05.15.
 */
public interface ExchangeRateRepository {

    void updateExchangeRateFor(ExchangeSides exchangeSides, Float exchangeRate);

    Float exchangeRateFor(ExchangeSides exchangeSides);

}
