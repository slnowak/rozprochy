package com.sr.client.bankmanager

import Bank._
import Ice._

/**
 * Created by novy on 17.05.15.
 */
object Client {

  def main(args: Array[String]) {
    try {
      run(args)
    }
    catch {
      case e: Any =>
        e.printStackTrace()
    }
  }

  def run(args: Array[String]) {
    val proxies: Map[accountType, String] = Map(accountType.SILVER -> "SilverAccounts.Proxy", accountType.PREMIUM -> "PremiumAccounts.Proxy")
    val categories: Map[accountType, String] = Map(accountType.SILVER -> "silverAccounts/", accountType.PREMIUM -> "premiumAccounts/")
    val communicator: Communicator = Ice.Util.initialize(args)

    val base: ObjectPrx = communicator.propertyToProxy("BankManager.Proxy")
    val bankManager: BankManagerPrx = BankManagerPrxHelper.checkedCast(base)

    while (true) {

    }

    val firstAccountStringHolder: StringHolder = new StringHolder
    val firstPersonalData: PersonalData = new PersonalData("s", "s", "s", "s")
    bankManager.createAccount(firstPersonalData, accountType.SILVER, firstAccountStringHolder)

    val secondAccountStringHolder: StringHolder = new StringHolder
    val secondPersonalData: PersonalData = new PersonalData("s", "s", "s", "s")
    bankManager.createAccount(secondPersonalData, accountType.PREMIUM, secondAccountStringHolder)

    val firstAccountPrx: AccountPrx = AccountPrxHelper.checkedCast(
      communicator.stringToProxy(categories.get(accountType.SILVER).get + firstAccountStringHolder.value + ":" +
        communicator.getProperties.getProperty(proxies.get(accountType.SILVER).get).split(":")(1))
    )

    val secondAccountPrx: PremiumAccountPrx = PremiumAccountPrxHelper.checkedCast(
      communicator.stringToProxy(categories.get(accountType.PREMIUM).get + secondAccountStringHolder.value + ":" +
        communicator.getProperties.getProperty(proxies.get(accountType.PREMIUM).get).split(":")(1))
    )

    System.out.println("\n\n------BEFORE MONEY TRANSFER------")
    System.out.println("first account number: " + firstAccountPrx.getAccountNumber)
    System.out.println("first account balance: " + firstAccountPrx.getBalance)
    System.out.println("second account number: " + secondAccountPrx.getAccountNumber)
    System.out.println("second account balance " + secondAccountPrx.getBalance)

    firstAccountPrx.transferMoney(secondAccountPrx.getAccountNumber, 3000)

    System.out.println("\n\n------AFTER MONEY TRANSFER------")
    System.out.println("first account number: " + firstAccountPrx.getAccountNumber)
    System.out.println("first account balance: " + firstAccountPrx.getBalance)
    System.out.println("second account number: " + secondAccountPrx.getAccountNumber)
    System.out.println("second account balance: " + secondAccountPrx.getBalance)

    // calculate loan
    val interestRateHolder: FloatHolder = new FloatHolder
    val totalLoanCostHolder: IntHolder = new IntHolder
    secondAccountPrx.calculateLoan(15000, Currency.USD, 15, interestRateHolder, totalLoanCostHolder)

    System.out.println("interest: " + interestRateHolder.value)
    System.out.println("total cost: " + totalLoanCostHolder.value)


    //    communicator = Util.initialize(args)
    //    val obj: ObjectPrx = communicator.propertyToProxy("Bank.Proxy")
    //    val manager: BankManagerPrx = BankManagerPrxHelper.checkedCast(obj)
    //    if (obj == null) {
    //      System.err.println("invalid proxy")
    //      return
    //    }
    //    val premiumAccountID: StringHolder = new StringHolder
    //    val silverAccountID: StringHolder = new StringHolder
    //    val premiumPersonalData: PersonalData = new PersonalData("Mateusz", "Jaje", "92082108754")
    //    val silverPersonalData: PersonalData = new PersonalData("John", "Kowalsky", "543082108754")
    //    manager.createAccount(premiumPersonalData, accountType.PREMIUM, premiumAccountID)
    //    manager.createAccount(silverPersonalData, accountType.SILVER, silverAccountID)
    //    println(s"received accountID %${premiumAccountID.value}")
    //    println(s"assert received account %${premiumAccountID.value}s=${getAccount(accountType.PREMIUM, premiumAccountID.value, manager).getAccountNumber}")
    //    getAccount(accountType.PREMIUM, premiumAccountID.value, manager).transfer(silverAccountID.value, 20)
    //    val i: PremiumAccountPrx = getAccount(accountType.PREMIUM, premiumAccountID.value, manager).asInstanceOf[PremiumAccountPrx]
    //    val interestRate: FloatHolder = new FloatHolder
    //    val totalCost: IntHolder = new IntHolder
    //    val amount: Int = 100 * 100
    //    val curr: currency = currency.CHF
    //    val period: Int = 5
    //    i.calculateLoan(amount, curr, period, totalCost, interestRate)
    //    println(s"loan $amount of $curr, rate ${interestRate.value}, cost ${totalCost.value}")
    //    manager.removeAccount(premiumAccountID.value)
    //    manager.removeAccount(silverAccountID.value)
    //  }
    //
    //  private def getAccount(`type`: accountType, accountID: String, bankManager: BankManagerPrx): AccountPrx = {
    //    val ice_getEndpoints: Array[Endpoint] = bankManager.ice_getEndpoints
    //    val proxy: ObjectPrx = communicator.stringToProxy(`type` + "/" + accountID + ":" + ice_getEndpoints(0))
    //    if (proxy == null) {
    //      System.err.println("invalid proxy")
    //      return null
    //    }
    //    `type` match {
    //      case accountType.SILVER =>
    //        AccountPrxHelper.checkedCast(proxy)
    //      case _ =>
    //        PremiumAccountPrxHelper.checkedCast(proxy)
    //    }
  }
}