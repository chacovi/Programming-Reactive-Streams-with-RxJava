package com.iteratrlearning.examples.synchronous.account;

import com.iteratrlearning.PortFinder;
import com.iteratrlearning.examples.synchronous.service.Service;

public class AccountService extends Service
{
    public static final int PORT = PortFinder.findAccountServicePort();

    public AccountService()
    {
        super(handler ->
        {
            handler.addServletWithMapping(AccountServlet.class, "/*");
        }, PORT);
    }

    public static void main(String[] args) throws Exception
    {
        new AccountService().run();
    }
}
