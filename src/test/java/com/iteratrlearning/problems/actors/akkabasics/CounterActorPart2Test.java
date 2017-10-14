package com.iteratrlearning.problems.actors.akkabasics;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;

@Ignore
public class CounterActorPart2Test {

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
    public void testTellCount() {
        Props props = Props.create(CounterActor.class);
        TestActorRef<CounterActor> ref = TestActorRef.create(system, props, "test-counter-actor");
        CounterActor actor = ref.underlyingActor();
        final int ITERATIONS = 5;
        for(int i = 0; i < ITERATIONS; i++) {
            ref.tell("Status", null);
        }

        // TODO: ask for the count
        CompletableFuture<Object> ask = null;

        assertEquals(ITERATIONS, ask.join());
    }

}
