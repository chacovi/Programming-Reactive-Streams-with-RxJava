package com.iteratrlearning.problems.asynchronous;

import com.iteratrlearning.answers.asynchronous.ShouldOfferMortgage;
import com.iteratrlearning.examples.synchronous.account.AccountService;
import com.iteratrlearning.examples.synchronous.credit_check.CreditCheckService;
import com.iteratrlearning.examples.synchronous.integration_tests.ServiceResource;
import org.apache.http.HttpStatus;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

@Ignore // TODO: remove Ignore
public class SimpleMortgageApplicationServiceTest
{

    @Rule
    public ServiceResource accountService = new ServiceResource(new AccountService());

    @Rule
    public ServiceResource creditCheckService = new ServiceResource(new CreditCheckService());

    @Rule
    public ServiceResource service = new ServiceResource(new SimpleMortgageApplicationService());

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

    private static void shouldOfferMortgage(final String amountToBorrow, final int responseCode)
        throws URISyntaxException, IOException
    {
        ShouldOfferMortgage.attempt(amountToBorrow, responseCode, SimpleMortgageApplicationService.PORT);
    }
}
