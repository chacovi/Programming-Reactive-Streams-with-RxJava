package com.iteratrlearning.examples.synchronous.service;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

enum Delayer implements DelayMXBean
{
    INST;

    static
    {
        try
        {
            final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            final ObjectName delayerName =
                new ObjectName("com.iteratrlearning.examples.synchronous.service:type=Delayer");

            mbs.registerMBean(INST, delayerName);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private volatile long delayInMs = 0;

    @Override
    public long getDelayInMs()
    {
        return delayInMs;
    }

    @Override
    public void setDelayInMs(final long delayInMs)
    {
        this.delayInMs = delayInMs;
    }

    void delay()
    {
        try
        {
            Thread.sleep(delayInMs);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
