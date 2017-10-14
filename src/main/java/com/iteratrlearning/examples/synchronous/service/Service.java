package com.iteratrlearning.examples.synchronous.service;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.ThreadPool.SizedThreadPool;

import java.util.function.Consumer;

public class Service
{
    private final Consumer<ServletHandler> servletConfiguration;
    private final Server server;

    public Service(final Consumer<ServletHandler> servletConfiguration, final int port)
    {
        this.servletConfiguration = servletConfiguration;
        server = new Server(port);

        final SizedThreadPool threadPool = (SizedThreadPool) server.getThreadPool();
        threadPool.setMaxThreads(10);
        threadPool.setMinThreads(10);
    }

    public void run() throws Exception
    {
        start();
        server.dumpStdErr();
        server.join();
    }

    public void start() throws Exception
    {
        // Force static constructor
        Delayer.INST.delay();

        final ServletHandler servletHandler = new ServletHandler();
        servletConfiguration.accept(servletHandler);

        server.setHandler(servletHandler);

        server.start();
    }

    public void stop() throws Exception
    {
        server.stop();
    }
}
