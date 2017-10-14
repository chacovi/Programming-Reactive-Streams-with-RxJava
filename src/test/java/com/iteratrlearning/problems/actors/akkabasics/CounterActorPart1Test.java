package com.iteratrlearning.problems.actors.akkabasics;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.PatternsCS;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import akka.util.Timeout;
import org.junit.*;
import scala.concurrent.duration.Duration;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore // TODO: remove Ignore
public class CounterActorPart1Test {

    private ActorSystem system;

    @Before
    public void setup() {
        system = ActorSystem.create();
    }

    @After
    public void teardown() {
        JavaTestKit.shutdownActorSystem(system);
    }

    @Test(timeout = 2_000)
    public void testNoMessagesSent() {
        Props props = Props.create(CounterActor.class);
        TestActorRef<CounterActor> ref = TestActorRef.create(system, props, "test-counter-actor");
        CounterActor actor = ref.underlyingActor();
        assertEquals(0, actor.getCount());
    }

    @Test(timeout = 2_000)
    public void testOneMessageSent() {
        Props props = Props.create(CounterActor.class);
        TestActorRef<CounterActor> ref = TestActorRef.create(system, props, "test-counter-actor");
        CounterActor actor = ref.underlyingActor();
        ref.tell("Status", null);
        assertEquals(1, actor.getCount());
    }

    @Test(timeout = 2_000)
    public void testFiveMessageSent() {
        Props props = Props.create(CounterActor.class);
        TestActorRef<CounterActor> ref = TestActorRef.create(system, props, "test-counter-actor");
        CounterActor actor = ref.underlyingActor();
        final int ITERATIONS = 5;
        for(int i = 0; i < ITERATIONS; i++) {
            ref.tell("Status", null);
        }

        assertEquals(ITERATIONS, actor.getCount());
    }

    @Test(timeout = 2_000)
    public void testStop() {
        Props props = Props.create(CounterActor.class);
        TestActorRef<CounterActor> ref = TestActorRef.create(system, props, "test-counter-actor");
        CounterActor actor = ref.underlyingActor();
        ref.tell("Status", null);
        ref.tell("Stop", null);

        assertTrue(ref.isTerminated());
        assertEquals(2, actor.getCount());
    }

}
