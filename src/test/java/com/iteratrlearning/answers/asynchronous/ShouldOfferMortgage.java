package com.iteratrlearning.answers.asynchronous;

import com.iteratrlearning.examples.synchronous.service.CustomerEndPoint;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class ShouldOfferMortgage
{
    public static void attempt(final String amountToBorrow, final int responseCode, final int port)
        throws URISyntaxException, IOException
    {
        Executor.closeIdleConnections();

        final URIBuilder uriBuilder = new URIBuilder("http://localhost:" + port)
            .addParameter(CustomerEndPoint.CUSTOMER_ID, "bob")
            .addParameter(MortgageApplicationService.AMOUNT_TO_BORROW, amountToBorrow);

        final HttpResponse response = Request.Get(uriBuilder.build()).execute().returnResponse();
        final int statusCode = response.getStatusLine().getStatusCode();

        assertEquals(responseCode, statusCode);
    }
}
