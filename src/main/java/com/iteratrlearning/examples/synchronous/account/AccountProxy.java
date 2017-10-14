package com.iteratrlearning.examples.synchronous.account;

import com.iteratrlearning.examples.synchronous.service.ServiceProxy;
import org.apache.http.client.utils.URIBuilder;

import static com.iteratrlearning.examples.synchronous.account.AccountService.PORT;
import static com.iteratrlearning.examples.synchronous.service.CustomerEndPoint.CUSTOMER_ID;

public class AccountProxy
{

    private final ServiceProxy service = new ServiceProxy();

    public BalanceReport getBalance(final String customer) throws Exception
    {
        final URIBuilder uriBuilder = new URIBuilder(service.localhost(PORT, "/"))
            .addParameter(CUSTOMER_ID, customer);

        return service.get(BalanceReport.class, uriBuilder);
    }
}
