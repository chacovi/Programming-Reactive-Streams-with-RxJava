package com.iteratrlearning.answers.actors.movie;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static akka.testkit.JavaTestKit.duration;

public class StorageActorTest {


    public static final String MOVIE1 = "StarWars";
    public static final String MOVIE2 = "JamesBond";
    ActorSystem system;

    @Test
    public void testNotWatchedMovie() {
        JavaTestKit probe = new JavaTestKit(system);
        final Props props = Props.create(StorageActor.class);
        final ActorRef subject = system.actorOf(props);
        subject.tell(new InfoMovieMessage(MOVIE1), probe.getRef());
        probe.expectMsgEquals(duration("1 second"), new InfoReplyMovieMessage(MOVIE1, 0));
    }

    @Test
    public void testWatchMovieOnce() {
        JavaTestKit probe = new JavaTestKit(system);
        final Props props = Props.create(StorageActor.class);
        final ActorRef subject = system.actorOf(props);
        subject.tell(new ViewMovieMessage("StarWars"), probe.getRef());
        subject.tell(new InfoMovieMessage(MOVIE1), probe.getRef());
        probe.expectMsgEquals(duration("1 second"), new InfoReplyMovieMessage(MOVIE1, 1));
    }

    @Test
    public void testWatchSeveralMovies() {
        JavaTestKit probe = new JavaTestKit(system);
        final Props props = Props.create(StorageActor.class);
        final ActorRef subject = system.actorOf(props);
        subject.tell(new ViewMovieMessage(MOVIE2), probe.getRef());
        subject.tell(new ViewMovieMessage(MOVIE1), probe.getRef());
        subject.tell(new ViewMovieMessage(MOVIE2), probe.getRef());
        subject.tell(new ViewMovieMessage(MOVIE2), probe.getRef());

        subject.tell(new InfoMovieMessage(MOVIE1), probe.getRef());
        subject.tell(new InfoMovieMessage(MOVIE2), probe.getRef());
        probe.expectMsgAllOf(duration("1 second"),
                new InfoReplyMovieMessage(MOVIE1, 1),
                new InfoReplyMovieMessage(MOVIE2, 3));
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
