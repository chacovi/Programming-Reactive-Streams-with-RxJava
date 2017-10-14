package com.iteratrlearning.examples.asynchronous.bank;

import com.iteratrlearning.examples.synchronous.account.BalanceReport;
import com.iteratrlearning.examples.synchronous.bank.MortgageReport;
import com.iteratrlearning.examples.synchronous.credit_check.CreditReport;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AsyncMortgageServlet extends AsyncCustomerEndPoint
{
    private final AsyncAccountProxy accountProxy = new AsyncAccountProxy(client, objectMapper);
    private final AsyncCreditCheckProxy creditProxy = new AsyncCreditCheckProxy(client, objectMapper);

    @Override
    protected void doGetCustomer(
        final String customer, final AsyncContext context) throws Exception
    {
        final CustomerHandler handler = new CustomerHandler(context);
        accountProxy.getBalance(customer, handler::onBalance, onError(context));
        creditProxy.getCreditReport(customer, handler::onCreditReport);
    }

    private class CustomerHandler
    {
        private final AsyncContext context;

        private BalanceReport balanceReport;
        private CreditReport creditReport;

        private CustomerHandler(final AsyncContext context)
        {
            this.context = context;
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
                final ServletResponse response = context.getResponse();
                try
                {
                    ok((HttpServletResponse) response);
                    writer.writeValue(response.getOutputStream(),
                        new MortgageReport(creditReport.getCreditScore(), balanceReport.getBalance()));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                context.complete();
            }
        }
    }

}
