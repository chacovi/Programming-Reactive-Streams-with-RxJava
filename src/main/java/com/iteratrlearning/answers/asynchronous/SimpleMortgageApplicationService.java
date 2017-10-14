package com.iteratrlearning.answers.asynchronous;

import com.iteratrlearning.PortFinder;
import com.iteratrlearning.examples.synchronous.service.Service;

public class SimpleMortgageApplicationService extends Service
{
    static final int PORT = PortFinder.findPort();

    public SimpleMortgageApplicationService()
    {
        super(handler ->
        {
            handler.addServletWithMapping(SimpleMortgageApplicationServlet.class, "/*");
        }, PORT);
    }

    public static void main(String[] args) throws Exception
    {
        new SimpleMortgageApplicationService().run();
    }
}
