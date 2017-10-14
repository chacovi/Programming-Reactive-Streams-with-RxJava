package com.iteratrlearning.examples.synchronous.bank;

import com.iteratrlearning.examples.synchronous.account.AccountProxy;
import com.iteratrlearning.examples.synchronous.account.BalanceReport;
import com.iteratrlearning.examples.synchronous.credit_check.CreditCheckProxy;
import com.iteratrlearning.examples.synchronous.credit_check.CreditReport;
import com.iteratrlearning.examples.synchronous.service.CustomerEndPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MortgageServlet extends CustomerEndPoint
{
    private final AccountProxy accountProxy = new AccountProxy();
    private final CreditCheckProxy creditCheckProxy = new CreditCheckProxy();

    @Override
    protected void doGetCustomer(
        final String customer,
        final HttpServletRequest request,
        final HttpServletResponse response)
        throws Exception
    {
        final BalanceReport balanceReport = accountProxy.getBalance(customer);
        final CreditReport creditReport = creditCheckProxy.getCreditReport(customer);

        writer.writeValue(response.getOutputStream(),
            new MortgageReport(creditReport.getCreditScore(), balanceReport.getBalance()));
    }
}
