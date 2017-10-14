package com.iteratrlearning.examples.synchronous.account;

import com.iteratrlearning.examples.synchronous.service.CustomerEndPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountServlet extends CustomerEndPoint
{
    @Override
    protected void doGetCustomer(
        final String customer,
        final HttpServletRequest request,
        final HttpServletResponse response)
        throws ServletException, IOException
    {
        writer.writeValue(response.getOutputStream(), new BalanceReport(123));
    }
}
