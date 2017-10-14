package com.iteratrlearning.examples.synchronous.credit_check;

import com.iteratrlearning.examples.synchronous.service.CustomerEndPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreditCheckServlet extends CustomerEndPoint
{
    public static final int DEFAULT_CREDIT_SCORE = 999;

    private static int creditScore = DEFAULT_CREDIT_SCORE;

    public static void setCreditScore(final int creditScore)
    {
        CreditCheckServlet.creditScore = creditScore;
    }

    @Override
    protected void doGetCustomer(
        final String customer,
        final HttpServletRequest request,
        final HttpServletResponse response)
        throws ServletException, IOException
    {
        writer.writeValue(response.getOutputStream(), new CreditReport(creditScore));
    }
}
