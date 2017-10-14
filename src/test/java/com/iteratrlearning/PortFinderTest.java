package com.iteratrlearning;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.assertTrue;

public class PortFinderTest
{
    @Test
    public void shouldFindFreePort() throws IOException
    {
        try (final ServerSocket block9090 = new ServerSocket(9090))
        {
            try (final ServerSocket block9091 = new ServerSocket(9091))
            {
                assertTrue(PortFinder.findPort() > 9091);
            }
        }
    }
}
