package com.iteratrlearning.problems.actors.akkabasics;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class PingPongApp {

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create();
        // Creates a SimpleActor and returns reference to it
        ActorRef pingActor
                = actorSystem.actorOf(Props.create(PingActor.class), "ping-actor");

        ActorRef pongActor
                = actorSystem.actorOf(Props.create(PongActor.class), "pong-actor");

        // when the program runs you should see PING PONG PING PONG PING ...
        pingActor.tell("PONG", pongActor);

    }
}

