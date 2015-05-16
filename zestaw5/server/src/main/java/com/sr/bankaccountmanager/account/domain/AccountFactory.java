package com.sr.bankaccountmanager.account.domain;

import Bank.PersonalData;
import Bank.RequestRejected;
import Bank.accountType;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by novy on 16.05.15.
 */
public class AccountFactory {

    private static final int DEFAULT_BALANCE = 10000;
    private final MoneyTransferService moneyTransferService;

    public AccountFactory(MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
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
        return new SilverAccount(randomAccountNumber(), personalData, DEFAULT_BALANCE, moneyTransferService);
    }

    private DomainAccount createPremiumAccount(PersonalData personalData) {
        return new PremiumAccount(
                createSilverAccount(personalData)
        );
    }

    private String randomAccountNumber() {
//        todo: implement
        return RandomStringUtils.randomNumeric(26);
    }

}
