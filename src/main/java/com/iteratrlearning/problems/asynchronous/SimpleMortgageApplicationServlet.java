package com.iteratrlearning.problems.asynchronous;

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

        // TODO: implement this method
        // Hint: you can use the same handler approach as in AsyncCurrentAccountServlet
        // Hint: you can use onError(context) for getBalance()'s second parameter

        final HttpServletResponse response = (HttpServletResponse) context.getResponse();
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        context.complete();
    }

}
