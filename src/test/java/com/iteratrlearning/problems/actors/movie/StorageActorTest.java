package com.iteratrlearning.problems.actors.movie;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.fail;

@Ignore
public class StorageActorTest {

    public static final String MOVIE1 = "StarWars";
    public static final String MOVIE2 = "JamesBond";
    ActorSystem system;

    @Test
    public void testNotWatchedMovie() {
        JavaTestKit probe = new JavaTestKit(system);
        final Props props = Props.create(StorageActor.class);
        final ActorRef storageActor = system.actorOf(props);
        storageActor.tell(new InfoMovieMessage(MOVIE1), probe.getRef());

        //TODO: assert using the probe that the reply is InfoReplyMovieMessage(MOVIE1, 0)
        fail();
    }

    @Test
    public void testWatchMovieOnce() {
        JavaTestKit probe = new JavaTestKit(system);
        final Props props = Props.create(StorageActor.class);
        final ActorRef storageActor = system.actorOf(props);

        // TODO: send one ViewMovieMessage to storageActor
        // TODO: send one InfoMovieMessage to storageActor
        // TODO: assert using the probe that the reply is InfoReplyMovieMessage(MOVIE1, 1)
        fail();
    }

    @Test
    public void testWatchSeveralMovies() {
        JavaTestKit probe = new JavaTestKit(system);
        final Props props = Props.create(StorageActor.class);
        final ActorRef storageActor = system.actorOf(props);

        // TODO: send two ViewMovieMessage(MOVIE1)
        // TODO: send three ViewMovieMessage(MOVIE2)
        // TODO: send one InfoMovieMessage(MOVIE1)
        // TODO: send one InfoMovieMessage(MOVIE2)
        // TODO: assert using the probe that correct replies are returned
        // Note: you can use probe.expectMsgAllOf
        fail();
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
