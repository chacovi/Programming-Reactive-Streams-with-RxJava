package com.iteratrlearning.examples.actors.supervision;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.stream.ConnectionException;
import scala.Option;

import java.io.IOException;
import java.util.logging.Logger;

public class RatingActor extends UntypedActor {

    int total = 0;
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void preRestart(Throwable reason,  Option<Object> message) {
        System.out.println("preRestarting FeedbackAnalyserActor");
        System.out.println("The message was " + message);
        System.err.println(reason);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info("Received Message inside FeedbackAnalyser: " + message);

        if(message.equals("WRITE")) {
            throw new ConnectionException("Couldn't connect");
        } else {

            String rating = ((String) message).substring(0, 2);
            System.out.println(rating);
            total += Integer.parseInt(rating);
            log.info("Total count:" + total);

        }

    }
}