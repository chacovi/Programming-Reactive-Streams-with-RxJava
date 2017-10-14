package com.iteratrlearning.answers.asynchronous;

import com.iteratrlearning.examples.asynchronous.bank.AsyncAccountProxy;
import com.iteratrlearning.examples.asynchronous.bank.AsyncCustomerEndPoint;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;

import static com.iteratrlearning.answers.asynchronous.MortgageApplicationService.AMOUNT_TO_BORROW;

public class SimpleMortgageApplicationServlet extends AsyncCustomerEndPoint
{
    private final AsyncAccountProxy accountProxy = new AsyncAccountProxy(client, objectMapper);

    @Override
    protected void doGetCustomer(
        final String customer, final AsyncContext context) throws Exception
    {
        final int amountToBorrow =
            Integer.parseInt(context.getRequest().getParameter(AMOUNT_TO_BORROW));

        accountProxy.getBalance(customer, balanceReport ->
        {
            final int balance = balanceReport.getBalance();
            final int maxAmountToBorrow = balance * 4;

            final HttpServletResponse response = (HttpServletResponse) context.getResponse();

            if (amountToBorrow <= maxAmountToBorrow)
            {
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else
            {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
            context.complete();
        },
        onError(context));
    }

}
