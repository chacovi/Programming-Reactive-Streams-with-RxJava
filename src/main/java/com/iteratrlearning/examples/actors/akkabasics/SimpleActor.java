package com.iteratrlearning.examples.actors.akkabasics;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SimpleActor extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private String greeting = "World";

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info("Received Message: " + message);
        log.info("Running in thread: " + Thread.currentThread().getName());

        if("Hello".equals(message)) {
            // replies back to sender
            getSender().tell(greeting, getSelf());
        }
    }

    public String getGreeting() {
        return this.greeting;
    }
}
