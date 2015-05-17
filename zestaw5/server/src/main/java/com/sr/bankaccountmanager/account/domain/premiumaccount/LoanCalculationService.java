package com.sr.bankaccountmanager.account.domain.premiumaccount;

import FinancialNews.Currency;
import com.sr.bankaccountmanager.news.domain.ExchangeRateRepository;
import com.sr.bankaccountmanager.news.domain.ExchangeSides;
import com.sr.bankaccountmanager.news.domain.InterestRepository;

/**
 * Created by novy on 16.05.15.
 */
public class LoanCalculationService {

    private static final float PER_MONTH_FACTOR = 0.01f;
    private final InterestRepository interestRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    public LoanCalculationService(InterestRepository interestRepository, ExchangeRateRepository exchangeRateRepository) {
        this.interestRepository = interestRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public LoanDetails calculateLoan(LoanCalculationData calculationData) {
        final ExchangeSides exchangeSides = calculationData.getExchangeSides();
        final float exchangedMoney = exchange(exchangeSides, calculationData.getAmount());
        final float periodInterestRate = periodInterestRate(calculationData.getPeriod(), exchangeSides.to());
        final float totalCostInForeignCurrency = totalCost(exchangedMoney, periodInterestRate);
        final float totalCostInNationalCurrency = exchange(
                ExchangeSides.of(exchangeSides.to(), exchangeSides.from()), totalCostInForeignCurrency
        );

        return new LoanDetails(
                interestRepository.interestRateFor(exchangeSides.to()),
                (int) totalCostInNationalCurrency
        );

    }

    private float exchange(ExchangeSides exchangeSides, float amount) {
        final Float exchangeRate = exchangeRateRepository.exchangeRateFor(exchangeSides);
        return amount / exchangeRate;
    }

    private float periodInterestRate(int period, Currency currency) {
        final float rate = interestRepository.interestRateFor(currency);
        return rate + period * PER_MONTH_FACTOR;
    }


    private float totalCost(float exchangedAmount, float periodInterestRate) {
        return exchangedAmount + exchangedAmount * periodInterestRate;
    }
}
