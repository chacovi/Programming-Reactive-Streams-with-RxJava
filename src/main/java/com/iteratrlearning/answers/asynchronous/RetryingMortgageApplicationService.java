package com.iteratrlearning.answers.asynchronous;

import com.iteratrlearning.PortFinder;
import com.iteratrlearning.examples.synchronous.service.Service;

public class RetryingMortgageApplicationService extends Service
{
    static final int PORT = PortFinder.findPort();

    public static final String AMOUNT_TO_BORROW = "amountToBorrow";

    public RetryingMortgageApplicationService()
    {
        super(handler ->
        {
            handler.addServletWithMapping(RetryingMortgageApplicationServlet.class, "/*");
        }, PORT);
    }

    public static void main(String[] args) throws Exception
    {
        new RetryingMortgageApplicationService().run();
    }
}
