package com.sr.bankaccountmanager.account.domain.premiumaccount;

import Bank.Currency;
import Bank.IncorrectData;
import com.sr.bankaccountmanager.news.domain.ExchangeSides;

/**
 * Created by novy on 16.05.15.
 */
public class LoanCalculationData {

    private final int amount;
    private final ExchangeSides exchangeSides;
    private final int period;

    public LoanCalculationData(int amount, Currency givenCurrency, Currency desiredCurrency, int period) throws IncorrectData {
        if (!amountValid(amount) || !periodValid(period)) {
            throw new IncorrectData();
        }

        this.amount = amount;
        this.exchangeSides = ExchangeSides.of(
                mapFrom(givenCurrency), mapFrom(desiredCurrency)
        );
        this.period = period;
    }

    private boolean amountValid(int amount) {
        return amount >= 0;
    }

    private boolean periodValid(int period) {
        return period > 1;
    }

    public int getAmount() {
        return amount;
    }

    public ExchangeSides getExchangeSides() {
        return exchangeSides;
    }

    public int getPeriod() {
        return period;
    }

    private FinancialNews.Currency mapFrom(Currency currency) {
        return FinancialNews.Currency.valueOf(
                currency.value()
        );
    }
}
