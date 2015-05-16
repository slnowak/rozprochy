package com.sr.bankaccountmanager.news.domain;


import FinancialNews.Currency;

/**
 * Created by novy on 16.05.15.
 */
public interface InterestRepository {

    void updateInterestRateFor(Currency currency, Float interest);

    Float interestRateFor(Currency currency);
}
