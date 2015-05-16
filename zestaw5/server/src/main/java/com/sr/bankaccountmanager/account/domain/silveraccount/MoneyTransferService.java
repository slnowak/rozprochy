package com.sr.bankaccountmanager.account.domain.silveraccount;

import Bank.IncorrectAccountNumber;
import com.sr.bankaccountmanager.account.domain.DomainAccount;

import java.util.Optional;

/**
 * Created by novy on 16.05.15.
 */
public class MoneyTransferService {

    private final ByAccountNumberQuery repository;

    public MoneyTransferService(ByAccountNumberQuery repository) {
        this.repository = repository;
    }

    public void makeTransfer(MoneyTransfer moneyTransfer) throws IncorrectAccountNumber {

        final Optional<DomainAccount> sourceOptionalAccount = repository.getByAccountNumber(moneyTransfer.getSourceAccountNumber());
        final Optional<DomainAccount> destinationOptionalAccount = repository.getByAccountNumber(moneyTransfer.getDestinationAccountNumber());

        if (!sourceOptionalAccount.isPresent() || !destinationOptionalAccount.isPresent()) {
            throw new IncorrectAccountNumber();
        }

        final DomainAccount sourceAccount = sourceOptionalAccount.get();
        final DomainAccount destinationAccount = destinationOptionalAccount.get();

        final int amount = moneyTransfer.getAmount();
        sourceAccount.decreaseBalance(amount);
        destinationAccount.increaseBalance(amount);
    }
}
