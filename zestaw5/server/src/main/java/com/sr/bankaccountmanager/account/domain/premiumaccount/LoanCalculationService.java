package com.sr.bankaccountmanager.account.domain.premiumaccount;

import com.sr.bankaccountmanager.news.domain.ExchangeRateRepository;
import com.sr.bankaccountmanager.news.domain.ExchangeSides;
import com.sr.bankaccountmanager.news.domain.InterestRepository;

/**
 * Created by novy on 16.05.15.
 */
public class LoanCalculationService {

    private final InterestRepository interestRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    public LoanCalculationService(InterestRepository interestRepository, ExchangeRateRepository exchangeRateRepository) {
        this.interestRepository = interestRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public LoanDetails calculateLoan(LoanCalculationData calculationData) {
        final ExchangeSides exchangeSides = calculationData.getExchangeSides();
        final Float interestRate = interestRepository.interestRateFor(exchangeSides.to());

        final Float exchangeRate = exchangeRateRepository.exchangeRateFor(exchangeSides);
        // todo: implement some logic
        return new LoanDetails(interestRate, (int)(10000 + exchangeRate * 1000));

    }
}
