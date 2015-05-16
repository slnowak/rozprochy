package com.sr.bankaccountmanager.news.domain;

import FinancialNews.Currency;

/**
 * Created by novy on 16.05.15.
 */
public class ExchangeSides {

    private final Currency from;
    private final Currency to;

    public static ExchangeSides of(Currency from, Currency to) {
        return new ExchangeSides(from, to);
    }

    private ExchangeSides(Currency from, Currency to) {
        this.from = from;
        this.to = to;
    }

    public Currency from() {
        return from;
    }

    public Currency to() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExchangeSides that = (ExchangeSides) o;

        if (from != that.from) return false;
        return to == that.to;

    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }
}
