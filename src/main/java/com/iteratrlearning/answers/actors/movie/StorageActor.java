package com.iteratrlearning.answers.actors.movie;

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

        if (message instanceof ViewMovieMessage) {
            final String movie = ((ViewMovieMessage) message).getMovie();
            movieViews.merge(movie, 1, (oldValue, one) -> oldValue + one);
        } else if (message instanceof InfoMovieMessage) {
            final String movie = ((InfoMovieMessage) message).getMovie();
            Integer count = movieViews.getOrDefault(movie, 0);
            getSender().tell(new InfoReplyMovieMessage(movie, count), getSelf());
        }

        else if (message.equals("echo")) {
            log.info("ECHO!");
        }
    }
}
