package com.iteratrlearning.answers.asynchronous;

import com.iteratrlearning.examples.synchronous.account.AccountService;
import com.iteratrlearning.examples.synchronous.credit_check.CreditCheckService;
import com.iteratrlearning.examples.synchronous.credit_check.CreditCheckServlet;
import com.iteratrlearning.examples.synchronous.integration_tests.ServiceResource;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.iteratrlearning.examples.synchronous.credit_check.CreditCheckServlet.DEFAULT_CREDIT_SCORE;

public class MortgageApplicationServiceTest
{

    @Rule
    public ServiceResource accountService = new ServiceResource(new AccountService());

    @Rule
    public ServiceResource creditCheckService = new ServiceResource(new CreditCheckService());

    @Rule
    public ServiceResource service = new ServiceResource(new MortgageApplicationService());

    @Test
    public void shouldOfferLowValueMortgage() throws Exception
    {
        shouldOfferMortgage("100", HttpStatus.SC_OK);
    }

    @Test
    public void shouldNotOfferHighValueMortgage() throws Exception
    {
        shouldOfferMortgage("1000", HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void shouldNotOfferMortgageWhenCreditScoreIsLow() throws Exception
    {
        CreditCheckServlet.setCreditScore(100);

        shouldOfferMortgage("1000", HttpStatus.SC_FORBIDDEN);
    }

    @After
    public void resetCreditScore()
    {
        CreditCheckServlet.setCreditScore(DEFAULT_CREDIT_SCORE);
    }

    private static void shouldOfferMortgage(final String amountToBorrow, final int responseCode)
        throws URISyntaxException, IOException
    {
        ShouldOfferMortgage.attempt(amountToBorrow, responseCode, MortgageApplicationService.PORT);
    }
}
