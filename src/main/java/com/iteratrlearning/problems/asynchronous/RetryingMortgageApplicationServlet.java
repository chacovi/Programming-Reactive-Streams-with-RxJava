package com.iteratrlearning.problems.asynchronous;

import com.iteratrlearning.examples.asynchronous.bank.AsyncAccountProxy;
import com.iteratrlearning.examples.asynchronous.bank.AsyncCreditCheckProxy;
import com.iteratrlearning.examples.asynchronous.bank.AsyncCustomerEndPoint;
import com.iteratrlearning.examples.synchronous.account.BalanceReport;
import com.iteratrlearning.examples.synchronous.credit_check.CreditReport;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;

import static com.iteratrlearning.answers.asynchronous.MortgageApplicationService.AMOUNT_TO_BORROW;

public class RetryingMortgageApplicationServlet extends AsyncCustomerEndPoint
{
    private final AsyncAccountProxy accountProxy = new AsyncAccountProxy(client, objectMapper);
    private final AsyncCreditCheckProxy creditProxy = new AsyncCreditCheckProxy(client, objectMapper);

    @Override
    protected void doGetCustomer(
        final String customer, final AsyncContext context) throws Exception
    {
        final int amountToBorrow =
            Integer.parseInt(context.getRequest().getParameter(AMOUNT_TO_BORROW));
        final CustomerHandler handler = new CustomerHandler(
            context, amountToBorrow, customer);
        // TODO: you need to implement the error callback in order to be able to implement the retry
        accountProxy.getBalance(customer, handler::onBalance, null);
        creditProxy.getCreditReport(customer, handler::onCreditReport);
    }

    private class CustomerHandler
    {
        private final AsyncContext context;
        private final int amountToBorrow;
        private final String customer;

        private BalanceReport balanceReport;
        private CreditReport creditReport;

        private CustomerHandler(
            final AsyncContext context, final int amountToBorrow, final String customer)
        {
            this.context = context;
            this.amountToBorrow = amountToBorrow;
            this.customer = customer;
        }

        private synchronized void onBalance(final BalanceReport balanceReport)
        {
            this.balanceReport = balanceReport;
            checkReports();
        }

        private synchronized void onCreditReport(final CreditReport creditReport)
        {
            this.creditReport = creditReport;
            checkReports();
        }

        private void checkReports()
        {
            if (balanceReport != null && creditReport != null)
            {
                final HttpServletResponse response = (HttpServletResponse) context.getResponse();
                final int creditScore = creditReport.getCreditScore();
                final int balance = balanceReport.getBalance();
                final int maxAmountToBorrow = balance * 4;

                if (creditScore > 700 && amountToBorrow <= maxAmountToBorrow)
                {
                    response.setStatus(HttpServletResponse.SC_OK);
                }
                else
                {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }

                context.complete();
            }
        }

        // TODO: implement the retry exercise
    }

}
