package com.knowledge.get.kotlin

fun main() {
    val accounts: List<InterestBearingAccount> = listOf(
        SavingsAccount("123", "Dmytro", 1000.0),
        SavingsAccount("456", "Anna", 2000.0)
    )

    accounts.forEach { it.applyInterest() }
}

open class BankAccount(open val accountNumber: String,
                       open val owner: String,
                       protected var balance: Double = 0.0) {
    private val transactionLog: MutableList<String> = mutableListOf()

    fun deposit(amount: Double) {
        validateAmount(amount)
        balance += amount
        transactionLog.add("Deposited $amount")
    }

    open fun withdraw(amount: Double) {
        validateAmount(amount)
        if (hasInsufficientBalance(amount)) {
            throw IllegalArgumentException("Insufficient balance: $amount")
        }
        balance -= amount
        transactionLog.add("Withdrew $amount")
    }

    fun printTransactions() {
        transactionLog.forEach{ println(it) }
    }

    private fun validateAmount(amount: Double) {
        if (amount < 0) {
            throw IllegalArgumentException("Amount must be positive.")
        }
    }

    private fun hasInsufficientBalance(amount: Double) = amount > balance

    override fun toString(): String {
        return "Account number: $accountNumber\nOwner: $owner\nBalance: $balance"
    }
}

class SavingsAccount (
    accountNumber: String,
    owner: String,
    balance: Double = 0.0,
    private var interestRate: Double = .05,
    private val monthlyWithdrawLimit: Int = 5
) : BankAccount(accountNumber, owner, balance), InterestBearingAccount {
    private var numberOfWithdraws: Int = 0

    override fun applyInterest() {
        balance += balance * interestRate
    }

    override fun withdraw(amount: Double) {
        if (numberOfWithdraws >= monthlyWithdrawLimit) {
            throw IllegalArgumentException("Month withdraw limit exceeded.")
        }
        numberOfWithdraws++
        super.withdraw(amount)
    }
}

interface InterestBearingAccount {
    fun applyInterest()
}