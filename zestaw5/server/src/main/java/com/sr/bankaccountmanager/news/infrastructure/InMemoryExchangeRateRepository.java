package com.sr.bankaccountmanager.news.infrastructure;

import FinancialNews.Currency;
import com.google.common.collect.Maps;
import com.sr.bankaccountmanager.news.domain.ExchangeRateRepository;
import com.sr.bankaccountmanager.news.domain.ExchangeSides;

import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

/**
 * Created by novy on 16.05.15.
 */
public class InMemoryExchangeRateRepository implements ExchangeRateRepository {

    private final ConcurrentMap<ExchangeSides, Float> exchangeRates = Maps.newConcurrentMap();
    private static final float DEFAULT_EXCHANGE_RATE = 6.66f;

    public InMemoryExchangeRateRepository() {
        Stream.of(Currency.values())
                .forEach(
                        currency -> exchangeRates.put(ExchangeSides.of(currency, currency), 1.0f)
                );
    }

    @Override
    public void updateExchangeRateFor(ExchangeSides exchangeSides, Float exchangeRate) {
        exchangeRates.put(exchangeSides, exchangeRate);
    }

    @Override
    public Float exchangeRateFor(ExchangeSides exchangeSides) {
        return exchangeRates.getOrDefault(exchangeSides, DEFAULT_EXCHANGE_RATE);
    }
}
