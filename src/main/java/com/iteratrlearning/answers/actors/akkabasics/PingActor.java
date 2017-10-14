package com.iteratrlearning.answers.actors.akkabasics;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class PingActor extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info("Received Message: " + message);
        if("PONG".equals(message)) {
            getSender().tell("PING", getSelf());
        }
    }
}
