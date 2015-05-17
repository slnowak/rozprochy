import Ice
from collections import Set
import sys
from Bank_ice import PersonalData, BankManagerPrx

__author__ = 'novy'

class Client(Ice.Application):

    def __init__(self, signalPolicy=0):
        super(Client, self).__init__(signalPolicy)
        self.silver_account_ids = Set()
        self.premium_account_ids = Set()
        self.bank_manager = BankManagerPrx.checkedCast(
            self.communicator().stringToProxy('BankManager.Proxy')
        )

    def print_menu(self):
        print "1 - Create silver account"
        print "2 - Create premium account"
        print "3 - Get balance"
        print "4 - transfer money"
        print "5 - calculate loan"
        print "6 - remove account"
        print "* - exit"

    def read_personal_data(self):
        print "Type first name: "
        first_name = sys.stdin.readline()

        print "Type last name: "
        last_name = sys.stdin.readline()

        print "Type nationality: "
        nationality = sys.stdin.readline()

        print "Type national id number: "
        national_id_number = sys.stdin.readline()

        return PersonalData(first_name, last_name, nationality, national_id_number)

    def run(self, args):
        self.shutdownOnInterrupt()

        while True:
            self.print_menu()
            operation = raw_input()

            if operation == '1':
                personal_data = self.read_personal_data()

            elif operation == '2':
                personal_data = self.read_personal_data()
            elif operation == '3':
                pass
            elif operation == '4':
                pass
            elif operation == '5':
                pass
            elif operation == '6':
                pass
            else:
                break


if __name__ == '__main__':
    app = Client()
    sys.exit(app.main(sys.argv))