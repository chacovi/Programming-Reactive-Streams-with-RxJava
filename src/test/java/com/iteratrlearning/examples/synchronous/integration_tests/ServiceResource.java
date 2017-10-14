package com.iteratrlearning.examples.synchronous.integration_tests;

import com.iteratrlearning.examples.synchronous.service.Service;
import org.junit.rules.ExternalResource;

public class ServiceResource extends ExternalResource
{
    private final Service service;

    public ServiceResource(final Service service)
    {
        this.service = service;
    }

    @Override
    protected void before() throws Throwable
    {
        service.start();
    }

    @Override
    protected void after()
    {
        try
        {
            service.stop();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
