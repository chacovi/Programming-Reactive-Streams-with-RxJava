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

import static akka.pattern.PatternsCS.ask;
import static org.junit.Assert.assertEquals;

@Ignore // TODO: remove Ignore
public class PingPongActorTest {

    private ActorSystem system;

    @Before
    public void setup() {
        system = ActorSystem.create();
    }

    @After
    public void teardown() {
        JavaTestKit.shutdownActorSystem(system);
    }

    @Test
    public void testPingPong() {
        TestActorRef<PingActor> pingRef
                = TestActorRef.create(system, Props.create(PingActor.class), "ping-actor");
        TestActorRef<PongActor> pongRef
                = TestActorRef.create(system, Props.create(PongActor.class), "pong-actor");

        CompletableFuture<Object> futurePing = ask(pingRef, "PONG", 1000).toCompletableFuture();
        CompletableFuture<Object> futurePong = ask(pongRef, "PING", 1000).toCompletableFuture();

        assertEquals("PING", futurePing.join());
        assertEquals("PONG", futurePong.join());
    }

}
