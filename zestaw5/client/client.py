import sys, Ice
import Bank

def create_account(bank_manager):
    first_name = raw_input('Enter First Name: ')
    last_name = raw_input('Enter Last Name: ')
    nationality = raw_input('Enter Nationality: ')
    national_id_number = raw_input('Enter National ID number: ')

    account_type = None
    while True:
        account_type_str = raw_input('Account type (silver or premium): ')
        if (account_type_str == 'silver') or (account_type_str == 'premium'):
            account_type = Bank.accountType.SILVER if account_type_str == 'silver' else Bank.accountType.PREMIUM
            break
        print 'Wrong account type'

    personal_data = Bank.PersonalData(firstName=first_name,
                                      lastName=last_name,
                                      nationality=nationality,
                                      nationalIDNumber=national_id_number)

    account_number = bank_manager.createAccount(personal_data, account_type)
    print 'Successfully created new account: %s' % (account_number, )

def remove_account(bank_manager):
    account_number = raw_input('Account number: ')

    try:
        bank_manager.removeAccount(account_number)
        print 'Account %s removed successfully' % (account_number, )
    except Bank.NoSuchAccount:
        print 'There is no such account'


def transfer_money(account):
    account_number = raw_input('Account number: ')
    amount = int(raw_input('Amount: '))

    try:
        account.transferMoney(account_number, amount)
        print 'Money (%d) transferred to %s' % (amount, account_number)
    except Bank.IncorrectAccountNumber:
        print 'Incorrect account number'
    except Bank.IncorrectAmount:
        print 'Incorrect amount'


def calculate_loan(account):
    amount = int(raw_input('Amount: '))
    currency = None
    while True:
        currencies = {
            'PLN': Bank.Currency.PLN,
            'EUR': Bank.Currency.EUR,
            'USD': Bank.Currency.USD,
            'CHF': Bank.Currency.CHF
        }

        currency_string = raw_input('Currency (PLN, EUR, USD, CHF): ')
        if currency_string in currencies:
            currency = currencies[currency_string]
            break

        print 'Incorrect currency'

    period = int(raw_input('Period: '))

    interest_rate, total_cost = account.calculateLoan(amount, currency, period)
    print "Interest rate: %f, total cost: %d" % (interest_rate, total_cost)


def construct_proxy(communicator, accountId, accountType):
    proxies = {
        'silver': 'SilverAccounts.Proxy',
        'premium': 'PremiumAccounts.Proxy'
    }

    prefix, suffix = communicator.getProperties().getProperty(proxies[accountType]).split(":")

    return prefix + accountId + ':' + suffix


def use_account(communicator):

    account_type = None
    while True:
        account_type = raw_input('Account type (silver or premium): ')
        if account_type in ('silver', 'premium'):
            break

        print 'Incorrect account type'

    account_number = raw_input('Account ID: ')

    try:
        if account_type == 'premium':
            account = Bank.PremiumAccountPrx.checkedCast(
                communicator.stringToProxy(
                    construct_proxy(communicator, account_number, account_type)
                )
            )
        elif account_type == 'silver':
            account = Bank.AccountPrx.checkedCast(
                communicator.stringToProxy(
                    construct_proxy(communicator, account_number, account_type)
                )
            )
    except Ice.ObjectNotExistException:
        print 'No such account'
        return

    print
    while True:
        print 'What would you like to do?'
        print '1 - get balance\n2 - get account number\n3 - transfer money\n4 - calculate loan\n5 - exit\n'
        action = int(raw_input())

        if action == 1:
            balance = account.getBalance()
            print 'Balance is %d' % (balance, )
        elif action == 2:
            account_number = account.getAccountNumber()
            print 'Account number: %s' % (account_number, )
        elif action == 3:
            transfer_money(account)
        elif action == 4:
            if account_type == 'silver':
                print "It's not a premium account"
                continue
            calculate_loan(account)
        elif action == 5:
            break
        print


if __name__ == '__main__':
    ic = Ice.initialize(sys.argv)
    bank_manager = Bank.BankManagerPrx.checkedCast(
        ic.propertyToProxy("BankManager.Proxy")
    )

    if not bank_manager:
        raise RuntimeError("Invalid proxy")

    while True:
        print 'What would you like to do?'
        print '1 - Create account\n2 - Remove account\n3 - Use your account\n4 - Exit'
        action = int(raw_input())

        if action == 1:
            create_account(bank_manager)
        elif action == 2:
            remove_account(bank_manager)
        elif action == 3:
            use_account(ic)
        elif action == 4:
            break
        else:
            print 'No such option, try again'
        print

    if ic:
        ic.destroy()