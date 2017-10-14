package com.iteratrlearning.problems.asynchronous;

import com.iteratrlearning.answers.asynchronous.FakeAccountService;
import com.iteratrlearning.answers.asynchronous.ShouldOfferMortgage;
import com.iteratrlearning.examples.synchronous.credit_check.CreditCheckService;
import com.iteratrlearning.examples.synchronous.integration_tests.ServiceResource;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.junit.Assert.assertEquals;

@Ignore
public class RetryingMortgageApplicationServiceTest
{

    @Rule
    public ServiceResource accountService = new ServiceResource(new FakeAccountService());

    @Rule
    public ServiceResource creditCheckService = new ServiceResource(new CreditCheckService());

    @Rule
    public ServiceResource service = new ServiceResource(new RetryingMortgageApplicationService());

    @Test
    public void shouldOfferLowValueMortgage() throws Exception
    {
        shouldOfferMortgage("100", SC_INTERNAL_SERVER_ERROR);

        final int requestCount = FakeAccountService.REQUEST_COUNT.get();
        assertEquals(requestCount, 10);
    }

    private static void shouldOfferMortgage(final String amountToBorrow, final int responseCode)
        throws URISyntaxException, IOException
    {
        ShouldOfferMortgage.attempt(amountToBorrow, responseCode, RetryingMortgageApplicationService.PORT);
    }

}
