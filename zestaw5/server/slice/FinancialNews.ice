
module FinancialNews {

  enum Currency {PLN, EUR, USD, CHF};


  interface FinancialNewsReceiver
  {
    void interestRate(float rate, Currency curr);   //np. (1,15%, EUR)
    void exchangeRate(float rate, Currency curr1, Currency curr2);   //np. (4,01, EUR, PLN)
  };


  interface FinancialNewsServer {
    void registerForNews (FinancialNewsReceiver* subscriber);
  };


};