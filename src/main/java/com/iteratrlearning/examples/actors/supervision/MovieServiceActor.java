package com.iteratrlearning.examples.actors.supervision;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.Option;
import scala.concurrent.duration.Duration;

public class MovieServiceActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final ActorRef ratingActor;


    // When actor fails, apply it to the actor, not all its children
    private OneForOneStrategy strategy = new OneForOneStrategy(10, Duration.create("1 minute"),
            t -> {
                if (t instanceof NumberFormatException) {
                    return OneForOneStrategy.resume();
                } else {
                    return OneForOneStrategy.escalate();
                }
            });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    public MovieServiceActor() {
        ratingActor = getContext().actorOf(Props.create(RatingActor.class));



    }

    @Override
    public void preRestart(Throwable reason,  Option<Object> message) {
        System.out.println("preRestarting the MovieServiceActor");
        System.out.println("The message was " + message);
        System.err.println(reason);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info("Received Message inside MovieService: " + message);


        ratingActor.tell(message, getSelf());


    }
}
