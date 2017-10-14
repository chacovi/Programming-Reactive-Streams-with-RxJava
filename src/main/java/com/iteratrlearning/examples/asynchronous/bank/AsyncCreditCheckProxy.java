package com.iteratrlearning.examples.asynchronous.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iteratrlearning.examples.synchronous.credit_check.CreditReport;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.util.function.Consumer;

import static com.iteratrlearning.examples.synchronous.credit_check.CreditCheckService.PORT;
import static com.iteratrlearning.examples.synchronous.service.CustomerEndPoint.CUSTOMER_ID;

public class AsyncCreditCheckProxy
{
    private final AsyncHttpClient client;
    private final ObjectMapper objectMapper;

    public AsyncCreditCheckProxy(final AsyncHttpClient client, final ObjectMapper objectMapper)
    {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public void getCreditReport(final String customerId, final Consumer<CreditReport> callback)
    {
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

                callback.accept(creditReport);

                return null;
            }
        });
    }
}
