package com.iteratrlearning.examples.synchronous.bank;

import com.iteratrlearning.examples.synchronous.account.BalanceReport;
import com.iteratrlearning.examples.synchronous.service.ServiceProxy;
import org.apache.http.client.utils.URIBuilder;

import static com.iteratrlearning.examples.synchronous.bank.BankService.ACCOUNT_URL;
import static com.iteratrlearning.examples.synchronous.bank.BankService.MORTGAGE_URL;

public class BankProxy
{
    private final ServiceProxy service = new ServiceProxy();
    private final int port;

    public BankProxy(final int port)
    {
        this.port = port;
    }

    public MortgageReport getMortgageReport(final String customerId) throws Exception
    {
        final URIBuilder uriBuilder = new URIBuilder(service.localhost(port, MORTGAGE_URL))
            .addParameter(BankService.CUSTOMER_ID, customerId);

        return service.get(MortgageReport.class, uriBuilder);
    }

    public BalanceReport getAccountBalance(final String customerId) throws Exception
    {
        final URIBuilder uriBuilder = new URIBuilder(service.localhost(port, ACCOUNT_URL))
            .addParameter(BankService.CUSTOMER_ID, customerId);

        return service.get(BalanceReport.class, uriBuilder);
    }
}
