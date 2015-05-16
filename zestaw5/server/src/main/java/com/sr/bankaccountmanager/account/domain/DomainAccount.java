package com.sr.bankaccountmanager.account.domain;

import Bank.Account;
import Bank.Currency;

import java.io.Serializable;

/**
 * Created by novy on 16.05.15.
 */
public interface DomainAccount extends Account, Serializable {

    Currency currency();

    void increaseBalance(int amount);

    void decreaseBalance(int amount);
}
