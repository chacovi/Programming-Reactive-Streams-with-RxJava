package com.iteratrlearning.examples.promises.bank;

import com.iteratrlearning.examples.synchronous.bank.MortgageReport;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CFMortgageServlet extends CFCustomerEndPoint
{
    private final CFAccountProxy accountProxy = new CFAccountProxy(client, objectMapper);
    private final CFCreditCheckProxy creditProxy = new CFCreditCheckProxy(client, objectMapper);

    @Override
    protected void doGetCustomer(
        final String customer, final AsyncContext context) throws Exception
    {
        accountProxy
            .getBalance(customer)
            .thenCombine(creditProxy.getCreditReport(customer), (balance, credit) ->
                    new MortgageReport(credit.getCreditScore(), balance.getBalance()))
            .thenAccept(report ->
            {
                final ServletResponse response = context.getResponse();
                try
                {
                    ok((HttpServletResponse) response);
                    writer.writeValue(response.getOutputStream(), report);
                    context.complete();
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            })
            .exceptionally(ex ->
            {
                onError(context);
                return null;
            });
    }

}
