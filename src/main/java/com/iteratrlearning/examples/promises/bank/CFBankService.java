package com.iteratrlearning.examples.promises.bank;

import com.iteratrlearning.PortFinder;
import com.iteratrlearning.examples.synchronous.service.Service;

public class CFBankService extends Service
{
    static final String CUSTOMER_ID = "customerId";
    static final String MORTGAGE_URL = "/mortgage/";
    static final String ACCOUNT_URL = "/account/";

    public static final int PORT = PortFinder.findBankServicePort();

    public CFBankService()
    {
        super(handler ->
        {
            handler.addServletWithMapping(CFMortgageServlet.class, MORTGAGE_URL + "*");
            handler.addServletWithMapping(CFCurrentAccountServlet.class, ACCOUNT_URL + "*");
        }, PORT);
    }

    public static void main(String[] args) throws Exception
    {
        new CFBankService().run();
    }
}
