package com.iteratrlearning.examples.synchronous.credit_check;

import com.iteratrlearning.PortFinder;
import com.iteratrlearning.examples.synchronous.service.Service;

public class CreditCheckService extends Service
{
    public static final int PORT = PortFinder.findCreditCheckServicePort();

    public CreditCheckService()
    {
        super(servletHandler ->
            servletHandler.addServletWithMapping(CreditCheckServlet.class, "/*"),
            PORT);
    }

    public static void main(String[] args) throws Exception
    {
        new CreditCheckService().run();
    }
}
