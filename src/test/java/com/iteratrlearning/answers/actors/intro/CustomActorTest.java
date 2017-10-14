package com.iteratrlearning.answers.actors.intro;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.function.BiConsumer;

import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class CustomActorTest
{

    private static final long TEST_TIMEOUT = 500L;

    private BiConsumer<CustomActor<String>, String> behaviourHandler = mock(BiConsumer.class);
    private BiConsumer<CustomActor<String>, Throwable> errorHandler = mock(BiConsumer.class);
    private CustomActor<String> actor;

    @Before
    public void setUp()
    {
        actor = ActorSystem.spawn(behaviourHandler, errorHandler);
    }

    @Test
    public void testAcceptMessage()
    {
        actor.send("Hello");

        attemptUntilPasses(() -> verify(behaviourHandler, times(1)).accept(actor, "Hello"));
    }

    @Test
    public void testErrorHandling()
    {
        Exception e = new RuntimeException("Fake Error");
        Mockito.doThrow(e).when(behaviourHandler).accept(actor, "Hello");

        actor.send("Hello");

        attemptUntilPasses(() -> verify(errorHandler, times(1)).accept(actor, e));
    }

    private static void attemptUntilPasses(final Runnable runnable)
    {
        final long limit = System.currentTimeMillis() + TEST_TIMEOUT;

        AssertionError lastThrowable = null;
        while (limit > System.currentTimeMillis())
        {
            try
            {
                runnable.run();
                lastThrowable = null;
                break;
            }
            catch (final AssertionError t)
            {
                lastThrowable = t;
            }
        }

        if (lastThrowable != null)
        {
            throw lastThrowable;
        }
    }
}
