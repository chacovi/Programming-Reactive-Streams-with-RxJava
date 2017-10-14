package com.iteratrlearning.examples.synchronous.credit_check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class CreditReport
{
    private static final int MAX_CREDIT_SCORE = 999;
    private static final int MIN_CREDIT_SCORE = 0;

    private final int creditScore;

    @JsonCreator
    public CreditReport(
        @JsonProperty("creditScore") final int creditScore)
    {
        if (creditScore < MIN_CREDIT_SCORE || creditScore > MAX_CREDIT_SCORE)
        {
            throw new IllegalArgumentException("Invalid credit score: " + creditScore);
        }
        this.creditScore = creditScore;
    }

    public int getCreditScore()
    {
        return creditScore;
    }
}
