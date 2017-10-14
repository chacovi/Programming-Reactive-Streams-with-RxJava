package com.iteratrlearning.problems.actors.movie;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.HashMap;
import java.util.Map;

public class StorageActor extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private Map<String, Integer> movieViews = new HashMap<>();

    public StorageActor() {
        log.info("StorageActor constructor");
    }

    @Override
    public void onReceive(Object message) throws Exception {

        log.info("Received Message: " + message);
        // TODO: deal with ViewMovieMessage (increments internal count for movie)
        // TODO: deal with InfoViewMessage (sends InfoReplyMovieMessage including the movie and views back to sender)
    }
}
