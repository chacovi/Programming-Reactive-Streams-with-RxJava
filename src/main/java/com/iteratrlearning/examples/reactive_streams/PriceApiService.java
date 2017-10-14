package com.iteratrlearning.examples.reactive_streams;

import com.iteratrlearning.examples.synchronous.service.Service;
import org.apache.http.client.fluent.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class PriceApiService extends Service
{
    private static final int PORT = 9091;
    private static final String LIMITED = "/limited";
    private static final String UNLIMITED = "/unlimited";

    public PriceApiService()
    {
        super(handler ->
        {
            handler.addServletWithMapping(RateLimitedServlet.class, LIMITED);
            handler.addServletWithMapping(UnlimitedServlet.class, UNLIMITED);
        }, PORT);
    }

    public static void main(String[] args) throws Exception
    {
        new PriceApiService().run();
    }

    public static String getPrice() throws IOException
    {
        return getPrice(UNLIMITED);
    }

    public static String getPriceLimited() throws IOException
    {
        return getPrice(LIMITED);
    }

    private static String getPrice(final String suffix) throws IOException
    {
        return Request
            .Get("http://localhost:" + PORT + suffix)
            .execute()
            .returnContent()
            .asString()
            .trim();
    }

    public static class UnlimitedServlet extends HttpServlet
    {
        @Override
        protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException
        {
            printPrice(resp);
        }
    }

    public static class RateLimitedServlet extends HttpServlet
    {
        private static final long ONE_SECOND = TimeUnit.SECONDS.toMillis(1);
        private static final int MAX_REQS_PER_SECOND = 2;

        private final Deque<Long> times = new ArrayDeque<>();

        @Override
        protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException
        {
            if (rateLimited())
            {
                printRateLimitError(resp);
            }
            else
            {
                printPrice(resp);
            }
        }

        private boolean rateLimited()
        {
            final long currentTimeMillis = System.currentTimeMillis();
            final long limit = currentTimeMillis - ONE_SECOND;
            synchronized (times)
            {
                while (!times.isEmpty() && times.peekLast() < limit)
                {
                    times.removeLast();
                }

                final boolean isRateLimited = times.size() > MAX_REQS_PER_SECOND;

                if (!isRateLimited)
                {
                    times.offerFirst(currentTimeMillis);
                }

                return isRateLimited;
            }
        }

        private void printRateLimitError(final HttpServletResponse resp) throws IOException
        {
            resp.setContentType("application/text");
            resp.setStatus(500);
            resp.getWriter()
                .append("Internal Server Error\n")
                .close();
        }
    }

    private static void printPrice(final HttpServletResponse resp) throws IOException
    {
        final double price = ThreadLocalRandom.current().nextDouble(100, 200);

        resp.setContentType("application/text");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter()
            .append(String.valueOf(price))
            .append('\n')
            .close();
    }
}
