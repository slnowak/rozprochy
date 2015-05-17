
module Bank {

  exception IncorrectData { string reason; };
  exception RequestRejected { string reason; };
  exception IncorrectAccountNumber {};
  exception IncorrectAmount {};
  exception NoSuchAccount {};

  enum Currency {PLN, EUR, USD, CHF};

  enum accountType {SILVER, PREMIUM};

  // amount: kwota w walucie krajowej (PLN) przemno¿ona przez 100 - 11,50 z³ to 1150

  interface Account {
    int getBalance ();
    string getAccountNumber();
    void transferMoney(string accountNumber, int amount) throws IncorrectAccountNumber, IncorrectAmount;
  };


  // amount: kwota w walucie krajowej (PLN) przemno¿ona przez 100 - 11,50 z³ to 1150
  // curr: waluta, w której ma byæ wziêty kredyt, w³¹czaj¹c PLN
  // period: czas w miesi¹cach
  // interestRate: aktualne ³¹czne oprocentowanie
  // totalCost: ca³kowity koszt kredytu w walucie narodowej w chwili bie¿¹cej
  // zwracane wielkoœci powinny braæ pod uwagê uzyskiwane z notyfikacji kursy waluty obcej i wysokoœæ jej oprocentowania na rynku bankowym, prowizjê banku oraz ewentualne zni¿ki dla wybranych klientów

  interface PremiumAccount extends Account {
    void calculateLoan(int amount, Currency curr, int period, out float interestRate, out int totalCost) throws IncorrectData;
  };


struct PersonalData
{
  string firstName;
  string lastName;
  string nationality;
  string nationalIDNumber; //PESEL
};


  //accountID: powinno byæ u¿ywane jako name w Identity identyfikuj¹cym konto.
  //System musi wiedzieæ, które konta aktualnie (od za³o¿enia do usuniêcia) istniej¹.

  interface BankManager
  {
    void createAccount(PersonalData data, accountType type, out string accountID) throws IncorrectData, RequestRejected;
    void removeAccount(string accountID) throws IncorrectData, NoSuchAccount;
  };
};

