package com.iteratrlearning.answers.asynchronous;

import com.iteratrlearning.examples.synchronous.account.AccountService;
import com.iteratrlearning.examples.synchronous.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeAccountService extends Service
{
    public static AtomicInteger REQUEST_COUNT = new AtomicInteger();

    public FakeAccountService()
    {
        super(handler ->
        {
            handler.addServletWithMapping(FakeAccountServlet.class, "/*");
        }, AccountService.PORT);
    }

    public static final class FakeAccountServlet extends HttpServlet
    {
        @Override
        protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException
        {
            REQUEST_COUNT.incrementAndGet();
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
