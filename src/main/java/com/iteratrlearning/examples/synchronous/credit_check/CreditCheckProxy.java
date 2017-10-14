package com.iteratrlearning.examples.synchronous.credit_check;

import com.iteratrlearning.examples.synchronous.service.ServiceProxy;
import org.apache.http.client.utils.URIBuilder;

import static com.iteratrlearning.examples.synchronous.credit_check.CreditCheckService.PORT;
import static com.iteratrlearning.examples.synchronous.service.CustomerEndPoint.CUSTOMER_ID;

public class CreditCheckProxy
{
    private ServiceProxy service = new ServiceProxy();

    public CreditReport getCreditReport(final String customer) throws Exception
    {
        final URIBuilder uriBuilder = new URIBuilder(service.localhost(PORT, "/"))
            .addParameter(CUSTOMER_ID, customer);

        return service.get(CreditReport.class, uriBuilder);
    }
}
