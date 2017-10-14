package com.iteratrlearning.examples.synchronous.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class CustomerEndPoint extends HttpServlet
{
    public static final String CUSTOMER_ID = "customerId";

    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();

    @Override
    protected void doGet(
        final HttpServletRequest request,
        final HttpServletResponse response)
        throws ServletException, IOException
    {
        final String customer = request.getParameter(CUSTOMER_ID);

        if ("bob".equals(customer))
        {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);

            Delayer.INST.delay();

            try
            {
                doGetCustomer(customer, request, response);
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

    protected abstract void doGetCustomer(
        final String customer,
        final HttpServletRequest request,
        final HttpServletResponse response)
        throws Exception;
}
