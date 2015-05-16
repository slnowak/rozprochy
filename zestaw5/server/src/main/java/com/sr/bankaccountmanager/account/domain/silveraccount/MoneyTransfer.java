package com.sr.bankaccountmanager.account.domain.silveraccount;

import Bank.IncorrectAccountNumber;
import Bank.IncorrectAmount;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by novy on 16.05.15.
 */
public class MoneyTransfer {

    private final String sourceAccountNumber;
    private final String destinationAccountNumber;
    private final int amount;

    public MoneyTransfer(String sourceAccountNumber, String destinationAccountNumber, int amount) throws IncorrectAccountNumber, IncorrectAmount {
        checkAccountNumber(sourceAccountNumber);
        checkAccountNumber(destinationAccountNumber);
        checkAmount(amount);

        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
    }

    private void checkAmount(int amount) throws IncorrectAmount {
        if (amount < 1) {
            throw new IncorrectAmount();
        }
    }

    private void checkAccountNumber(String accountNumber) throws IncorrectAccountNumber {
        if (!accountNumberValid(accountNumber)) {
            throw new IncorrectAccountNumber();
        }
    }

    private boolean accountNumberValid(String accountNumber) {
        return StringUtils.isNumeric(accountNumber) && accountNumber.length() == 26;
    }

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public int getAmount() {
        return amount;
    }
}
