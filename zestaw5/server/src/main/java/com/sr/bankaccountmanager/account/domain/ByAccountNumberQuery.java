package com.sr.bankaccountmanager.account.domain;

import java.util.Optional;

/**
 * Created by novy on 16.05.15.
 */
public interface ByAccountNumberQuery {

    Optional<DomainAccount> getByAccountNumber(String accountNumber);
}
