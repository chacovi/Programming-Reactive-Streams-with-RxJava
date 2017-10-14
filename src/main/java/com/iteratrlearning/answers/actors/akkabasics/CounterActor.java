package com.iteratrlearning.answers.actors.akkabasics;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class CounterActor extends UntypedActor {

    private int count = 0;
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info("Received Message: " + message);

        if("Count".equals(message)) {
            getSender().tell(count, getSelf());
        }
        else if("Stop".equals(message)) {
            getContext().stop(getSelf());
        }
        count++;
    }

    public int getCount() {
        return count;
    }
}
