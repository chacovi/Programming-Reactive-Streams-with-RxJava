package com.iteratrlearning.examples.synchronous.integration_tests;

import com.iteratrlearning.examples.synchronous.account.AccountService;
import com.iteratrlearning.examples.synchronous.account.BalanceReport;
import com.iteratrlearning.examples.synchronous.bank.BankProxy;
import com.iteratrlearning.examples.synchronous.bank.BankService;
import com.iteratrlearning.examples.synchronous.bank.MortgageReport;
import com.iteratrlearning.examples.synchronous.credit_check.CreditCheckService;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankServiceTest
{

    @Rule
    public ServiceResource accountService = new ServiceResource(new AccountService());

    @Rule
    public ServiceResource creditCheckService = new ServiceResource(new CreditCheckService());

    @Rule
    public ServiceResource service = new ServiceResource(new BankService());

    private BankProxy proxy = new BankProxy(BankService.PORT);

    @Test
    public void shouldReturnAccountBalance() throws Exception
    {
        final BalanceReport balanceReport = proxy.getAccountBalance("bob");

        assertEquals(balanceReport.getBalance(), 123);
    }

    @Test
    public void shouldReturnMortgageReport() throws Exception
    {
        final MortgageReport mortgageReport = proxy.getMortgageReport("bob");

        assertEquals(mortgageReport.getBalance(), 123);
        assertEquals(mortgageReport.getCreditScore(), 999);
    }
}
