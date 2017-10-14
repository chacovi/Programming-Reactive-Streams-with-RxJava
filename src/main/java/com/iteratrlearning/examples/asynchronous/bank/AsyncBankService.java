package com.iteratrlearning.examples.asynchronous.bank;

import com.iteratrlearning.PortFinder;
import com.iteratrlearning.examples.synchronous.service.Service;

public class AsyncBankService extends Service
{
    static final String CUSTOMER_ID = "customerId";
    static final String MORTGAGE_URL = "/mortgage/";
    static final String ACCOUNT_URL = "/account/";
    public static final int PORT = PortFinder.findBankServicePort();

    public AsyncBankService()
    {
        super(handler ->
        {
            handler.addServletWithMapping(AsyncMortgageServlet.class, MORTGAGE_URL + "*");
            handler.addServletWithMapping(AsyncCurrentAccountServlet.class, ACCOUNT_URL + "*");
        }, PORT);
    }

    public static void main(String[] args) throws Exception
    {
        new AsyncBankService().run();
    }
}
