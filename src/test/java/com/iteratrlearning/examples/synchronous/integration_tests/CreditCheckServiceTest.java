package com.iteratrlearning.examples.synchronous.integration_tests;

import com.iteratrlearning.examples.synchronous.credit_check.CreditCheckProxy;
import com.iteratrlearning.examples.synchronous.credit_check.CreditCheckService;
import com.iteratrlearning.examples.synchronous.credit_check.CreditReport;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreditCheckServiceTest
{

    @Rule
    public ServiceResource service = new ServiceResource(new CreditCheckService());

    private CreditCheckProxy proxy = new CreditCheckProxy();

    @Test
    public void shouldReturnCreditReport() throws Exception
    {
        final CreditReport creditReport = proxy.getCreditReport("bob");

        assertEquals(creditReport.getCreditScore(), 999);
    }
}
