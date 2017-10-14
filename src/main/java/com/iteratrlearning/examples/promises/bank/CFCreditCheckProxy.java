package com.iteratrlearning.examples.promises.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iteratrlearning.examples.synchronous.credit_check.CreditReport;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.util.concurrent.CompletableFuture;

import static com.iteratrlearning.examples.synchronous.credit_check.CreditCheckService.PORT;
import static com.iteratrlearning.examples.synchronous.service.CustomerEndPoint.CUSTOMER_ID;

public class CFCreditCheckProxy
{
    private final AsyncHttpClient client;
    private final ObjectMapper objectMapper;

    public CFCreditCheckProxy(final AsyncHttpClient client, final ObjectMapper objectMapper)
    {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public CompletableFuture<CreditReport> getCreditReport(final String customerId)
    {
        final CompletableFuture<CreditReport> creditFuture = new CompletableFuture<>();

        client
            .prepareGet("http://localhost:" + PORT + "/")
            .addQueryParam(CUSTOMER_ID, customerId)
            .execute(new AsyncCompletionHandler<String>()
        {
            @Override
            public String onCompleted(final Response remoteResponse) throws Exception
            {
                final CreditReport creditReport =
                    objectMapper.readValue(remoteResponse.getResponseBodyAsStream(), CreditReport.class);

                creditFuture.complete(creditReport);

                return null;
            }
        });

        return creditFuture;
    }
}
