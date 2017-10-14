package com.iteratrlearning.problems.actors.akkabasics;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class CounterActor extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        // TODO: should record number of times it receives messages
        // TODO: should stop actor when receiving "Stop"
        // TODO: log all messages
    }

    public int getCount() {
        return -1;
    }

}
