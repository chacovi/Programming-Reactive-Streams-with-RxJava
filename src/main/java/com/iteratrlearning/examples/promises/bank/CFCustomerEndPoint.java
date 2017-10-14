package com.iteratrlearning.examples.promises.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Consumer;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public abstract class CFCustomerEndPoint extends HttpServlet
{
    public static final String CUSTOMER_ID = "customerId";

    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
    protected final AsyncHttpClient client = new DefaultAsyncHttpClient();

    @Override
    protected void doGet(
        final HttpServletRequest request,
        final HttpServletResponse response)
        throws ServletException, IOException
    {
        final String customer = request.getParameter(CUSTOMER_ID);

        if ("bob".equals(customer))
        {
            try
            {
                doGetCustomer(customer, request.startAsync());
            }
            catch (final IOException | ServletException e)
            {
                throw e;
            }
            catch (final Exception e)
            {
                throw new ServletException(e);
            }
        }
        else
        {
            response.setContentType("application/text");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().append("Missing user").close();
        }
    }

    protected void ok(final HttpServletResponse response)
    {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected Consumer<Throwable> onError(final AsyncContext context)
    {
        return t ->
        {
            t.printStackTrace();

            final HttpServletResponse response = (HttpServletResponse) context.getResponse();
            response.setStatus(SC_INTERNAL_SERVER_ERROR);
            context.complete();
        };
    }

    protected abstract void doGetCustomer(
        final String customer,
        final AsyncContext context)
        throws Exception;
}
