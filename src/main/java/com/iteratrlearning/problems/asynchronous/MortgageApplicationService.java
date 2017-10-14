package com.iteratrlearning.problems.asynchronous;

import com.iteratrlearning.PortFinder;
import com.iteratrlearning.examples.synchronous.service.Service;

public class MortgageApplicationService extends Service
{
    static final int PORT = PortFinder.findPort();

    public static final String AMOUNT_TO_BORROW = "amountToBorrow";

    public MortgageApplicationService()
    {
        super(handler ->
        {
            handler.addServletWithMapping(MortgageApplicationServlet.class, "/*");
        }, PORT);
    }

    public static void main(String[] args) throws Exception
    {
        new MortgageApplicationService().run();
    }
}
