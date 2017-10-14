package com.iteratrlearning.examples.promises.bank;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CFCurrentAccountServlet extends CFCustomerEndPoint
{
    private final CFAccountProxy accountProxy = new CFAccountProxy(client, objectMapper);

    @Override
    protected void doGetCustomer(
        final String customer, final AsyncContext context) throws Exception
    {
        final ServletResponse response = context.getResponse();
        accountProxy
            .getBalance(customer)
            .whenComplete((balanceReport, ex) ->
            {
                if (ex != null)
                {

                    onError(context);
                }
                else
                {
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
                }
            });
    }

}
