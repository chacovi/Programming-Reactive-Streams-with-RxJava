package com.iteratrlearning.examples.promises.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iteratrlearning.examples.synchronous.account.BalanceReport;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.util.concurrent.CompletableFuture;

import static com.iteratrlearning.examples.synchronous.account.AccountService.PORT;
import static com.iteratrlearning.examples.synchronous.service.CustomerEndPoint.CUSTOMER_ID;

public class CFAccountProxy
{
    private final AsyncHttpClient client;
    private final ObjectMapper objectMapper;

    public CFAccountProxy(final AsyncHttpClient client, final ObjectMapper objectMapper)
    {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public CompletableFuture<BalanceReport> getBalance(
        final String customerId)
    {
        final CompletableFuture<BalanceReport> reportFuture = new CompletableFuture<>();
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

                reportFuture.complete(balanceReport);

                return null;
            }

            @Override
            public void onThrowable(final Throwable ex)
            {
                reportFuture.completeExceptionally(ex);
            }
        });

        return reportFuture;
    }
}
