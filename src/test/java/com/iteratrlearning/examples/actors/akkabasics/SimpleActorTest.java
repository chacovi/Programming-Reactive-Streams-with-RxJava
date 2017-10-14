package com.iteratrlearning.examples.actors.akkabasics;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.PatternsCS;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import org.junit.*;

import java.util.concurrent.CompletableFuture;

import static akka.testkit.JavaTestKit.duration;
import static org.junit.Assert.assertEquals;

public class SimpleActorTest {

    ActorSystem system;

    @Test
    public void testTellHello() {
        Props props = Props.create(SimpleActor.class);
        TestActorRef<SimpleActor> ref = TestActorRef.create(system, props, "test-simple-actor");
        // access actor methods and state
        SimpleActor actor = ref.underlyingActor();
        // actor.getGreeting();
        ref.tell("Hello", null);
    }

    @Test
    public void testAskHello() {
        Props props = Props.create(SimpleActor.class);
        TestActorRef<SimpleActor> ref = TestActorRef.create(system, props, "test-simple-actor");
        CompletableFuture<Object> future = PatternsCS.ask(ref, "Hello", 1000).toCompletableFuture();
        assertEquals("World", future.join());
    }

    @Test
    public void testTellWithProbe() {
        JavaTestKit probe = new JavaTestKit(system);
        Props props = Props.create(SimpleActor.class);
        ActorRef subject = system.actorOf(props);

        subject.tell("Hello", probe.getRef());
        //subject.tell("Hello", probe.getRef());
        // await the correct responses
        probe.expectMsgEquals(duration("1 second"), "World");
        //probe.expectMsgEquals(duration("1 second"), "World");
    }

    @Before
    public void setup() {
        system = ActorSystem.create();
    }

    @After
    public void teardown() {
        JavaTestKit.shutdownActorSystem(system);
    }

}
