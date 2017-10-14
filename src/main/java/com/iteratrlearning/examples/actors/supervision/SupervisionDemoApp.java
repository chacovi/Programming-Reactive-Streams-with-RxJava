package com.iteratrlearning.examples.actors.supervision;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.Props;

public class SupervisionDemoApp {

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create();
        ActorRef movieServiceActor
                = actorSystem.actorOf(Props.create(MovieServiceActor.class), "movie-service-actor");
        movieServiceActor.tell("60", null);
        movieServiceActor.tell("40", null);
        movieServiceActor.tell("ab", null);
        movieServiceActor.tell("30", null);
//        movieServiceActor.tell("80", null);
        movieServiceActor.tell("WRITE", null);
        movieServiceActor.tell("20", null);
    }
}
