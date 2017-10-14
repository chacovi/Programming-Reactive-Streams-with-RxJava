package com.iteratrlearning.answers.actors.pipes;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.Base64;

public class Base64EncoderActor extends UntypedActor {

    private ActorRef nextActor;
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public Base64EncoderActor(ActorRef nextActor) {
        this.nextActor = nextActor;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info("Received Message: " + message);
        String msg = (String) message;
        byte[] result =  Base64.getEncoder().encode(msg.getBytes());
        nextActor.tell(new String(result), getSelf());
    }
}
