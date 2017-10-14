package com.iteratrlearning.examples.asynchronous.bank;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AsyncCurrentAccountServlet extends AsyncCustomerEndPoint
{
    private final AsyncAccountProxy accountProxy = new AsyncAccountProxy(client, objectMapper);

    @Override
    protected void doGetCustomer(
        final String customer, final AsyncContext context) throws Exception
    {
        accountProxy.getBalance(customer, balanceReport ->
        {
            final ServletResponse response = context.getResponse();
            try
            {
                ok((HttpServletResponse) response);
                writer.writeValue(response.getOutputStream(), balanceReport);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            context.complete();
        }, onError(context));
    }

}
