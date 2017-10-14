package com.iteratrlearning.examples.synchronous.bank;

import com.iteratrlearning.PortFinder;
import com.iteratrlearning.examples.synchronous.service.Service;

public class BankService extends Service
{
    static final String CUSTOMER_ID = "customerId";
    static final String MORTGAGE_URL = "/mortgage/";
    static final String ACCOUNT_URL = "/account/";
    public static final int PORT = PortFinder.findBankServicePort();

    public BankService()
    {
        super(handler ->
        {
            handler.addServletWithMapping(MortgageServlet.class, MORTGAGE_URL + "*");
            handler.addServletWithMapping(CurrentAccountServlet.class, ACCOUNT_URL + "*");
        }, PORT);
    }

    public static void main(String[] args) throws Exception
    {
        new BankService().run();
    }
}
