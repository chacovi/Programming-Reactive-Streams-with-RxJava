package com.iteratrlearning.examples.synchronous.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class BalanceReport
{
    private final int balance;

    @JsonCreator
    public BalanceReport(
        @JsonProperty("balance") final int balance)
    {
        this.balance = balance;
    }

    public int getBalance()
    {
        return balance;
    }
}
