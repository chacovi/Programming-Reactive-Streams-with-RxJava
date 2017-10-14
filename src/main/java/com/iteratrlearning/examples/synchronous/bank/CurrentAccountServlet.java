package com.iteratrlearning.examples.synchronous.bank;

import com.iteratrlearning.examples.synchronous.account.AccountProxy;
import com.iteratrlearning.examples.synchronous.account.BalanceReport;
import com.iteratrlearning.examples.synchronous.service.CustomerEndPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CurrentAccountServlet extends CustomerEndPoint
{
    private final AccountProxy accountProxy = new AccountProxy();

    @Override
    protected void doGetCustomer(
        final String customer,
        final HttpServletRequest request,
        final HttpServletResponse response)
        throws Exception
    {
        final BalanceReport balanceReport = accountProxy.getBalance(customer);

        writer.writeValue(response.getOutputStream(), balanceReport);
    }
}
