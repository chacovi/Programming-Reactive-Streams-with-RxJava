package com.iteratrlearning;

import java.io.IOException;
import java.net.ServerSocket;

public class PortFinder
{
    private static final int START_PORT = 9090;
    private static final int MAX_ATTEMPTS = 10;

    private static int candidatePort = START_PORT;

    public static int findPort()
    {
        for (int i = 0; i < MAX_ATTEMPTS; i++)
        {
            try
            {
                try (final ServerSocket serverSocket = new ServerSocket(candidatePort))
                {
                    if (serverSocket.isBound())
                    {
                        final int port = candidatePort;
                        System.out.println("********* Found Port: " + port);
                        candidatePort++;
                        return port;
                    }
                }
            }
            catch (IOException e)
            {
                candidatePort++;
            }
        }

        throw new Error(
            "Unable to start service, attempted 10 ports, ranging from " +
            (candidatePort - 10) + " to " + candidatePort +
            ", maybe you have a firewall enabled?");
    }

    private static int findConfigurablePort(final String property)
    {
        final Integer value = Integer.getInteger(property);
        if (value == null)
        {
            return findPort();
        }

        return value;
    }

    public static int findBankServicePort()
    {
        return findConfigurablePort("bank.port");
    }

    public static int findCreditCheckServicePort()
    {
        return findConfigurablePort("creditCheck.port");
    }

    public static int findAccountServicePort()
    {
        return findConfigurablePort("account.port");
    }

}
