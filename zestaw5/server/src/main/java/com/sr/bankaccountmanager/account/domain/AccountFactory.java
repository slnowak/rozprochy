package com.sr.bankaccountmanager.account.domain;

import Bank.Currency;
import Bank.PersonalData;
import Bank.RequestRejected;
import Bank.accountType;
import com.sr.bankaccountmanager.account.domain.premiumaccount.LoanCalculationService;
import com.sr.bankaccountmanager.account.domain.premiumaccount.PremiumAccount;
import com.sr.bankaccountmanager.account.domain.silveraccount.MoneyTransferService;
import com.sr.bankaccountmanager.account.domain.silveraccount.SilverAccount;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by novy on 16.05.15.
 */
public class AccountFactory {

    private static final int DEFAULT_BALANCE = 10000;
    private final MoneyTransferService moneyTransferService;
    private final LoanCalculationService loanCalculationService;

    public AccountFactory(MoneyTransferService moneyTransferService, LoanCalculationService loanCalculationService) {
        this.moneyTransferService = moneyTransferService;
        this.loanCalculationService = loanCalculationService;
    }

    public DomainAccount createAccount(accountType accountType, PersonalData personalData) throws RequestRejected {
        switch (accountType) {
            case SILVER:
                return createSilverAccount(personalData);
            case PREMIUM:
                return createPremiumAccount(personalData);
            default:
                throw new RequestRejected();
        }
    }

    private DomainAccount createSilverAccount(PersonalData personalData) {
        return new SilverAccount(randomAccountNumber(), personalData, DEFAULT_BALANCE, Currency.PLN, moneyTransferService);
    }

    private DomainAccount createPremiumAccount(PersonalData personalData) {
        return new PremiumAccount(
                createSilverAccount(personalData), loanCalculationService
        );
    }

    private String randomAccountNumber() {
//        todo: implement
        return RandomStringUtils.randomNumeric(26);
    }

}
