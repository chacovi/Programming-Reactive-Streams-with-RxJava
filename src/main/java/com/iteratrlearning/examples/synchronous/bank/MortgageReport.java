package com.iteratrlearning.examples.synchronous.bank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class MortgageReport
{
    private final int creditScore;
    private final int balance;

    @JsonCreator
    public MortgageReport(
        @JsonProperty("creditScore") final int creditScore,
        @JsonProperty("balance") final int balance)
    {
        this.creditScore = creditScore;
        this.balance = balance;
    }

    public int getBalance()
    {
        return balance;
    }

    public int getCreditScore()
    {
        return creditScore;
    }
}
