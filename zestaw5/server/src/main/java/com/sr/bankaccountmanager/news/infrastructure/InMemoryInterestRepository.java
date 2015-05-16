package com.sr.bankaccountmanager.news.infrastructure;

import FinancialNews.Currency;
import com.google.common.collect.Maps;
import com.sr.bankaccountmanager.news.domain.InterestRepository;

import java.util.concurrent.ConcurrentMap;

/**
 * Created by novy on 16.05.15.
 */
public class InMemoryInterestRepository implements InterestRepository {

    private final ConcurrentMap<Currency, Float> interests = Maps.newConcurrentMap();
    private static final float DEFAULT_INTEREST_RATE = 0.05f;


    @Override
    public void updateInterestRateFor(Currency currency, Float interest) {
        interests.put(currency, interest);
    }

    @Override
    public Float interestRateFor(Currency currency) {
        return interests.getOrDefault(currency, DEFAULT_INTEREST_RATE);
    }
}
