package com.iteratrlearning.examples.synchronous.integration_tests;

import com.iteratrlearning.examples.synchronous.account.AccountProxy;
import com.iteratrlearning.examples.synchronous.account.AccountService;
import com.iteratrlearning.examples.synchronous.account.BalanceReport;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountServiceTest
{

    @Rule
    public ServiceResource service = new ServiceResource(new AccountService());

    private AccountProxy proxy = new AccountProxy();

    @Test
    public void shouldReturnBalanceReport() throws Exception
    {
        final BalanceReport balanceReport = proxy.getBalance("bob");

        assertEquals(balanceReport.getBalance(), 123);
    }
}
