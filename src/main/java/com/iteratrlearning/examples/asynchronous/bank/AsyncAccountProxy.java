package com.iteratrlearning.examples.asynchronous.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iteratrlearning.examples.synchronous.account.BalanceReport;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.util.function.Consumer;

import static com.iteratrlearning.examples.synchronous.account.AccountService.PORT;
import static com.iteratrlearning.examples.synchronous.service.CustomerEndPoint.CUSTOMER_ID;

public class AsyncAccountProxy
{
    private final AsyncHttpClient client;
    private final ObjectMapper objectMapper;

    public AsyncAccountProxy(final AsyncHttpClient client, final ObjectMapper objectMapper)
    {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public void getBalance(
        final String customerId,
        final Consumer<BalanceReport> callback,
        final Consumer<Throwable> errorCallback)
    {
        client
            .prepareGet("http://localhost:" + PORT + "/")
            .addQueryParam(CUSTOMER_ID, customerId)
            .execute(new AsyncCompletionHandler<String>()
        {
            @Override
            public String onCompleted(final Response remoteResponse) throws Exception
            {
                final BalanceReport balanceReport =
                    objectMapper.readValue(remoteResponse.getResponseBodyAsStream(), BalanceReport.class);

                callback.accept(balanceReport);

                return null;
            }

            @Override
            public void onThrowable(final Throwable t)
            {
                errorCallback.accept(t);
            }
        });
    }
}
