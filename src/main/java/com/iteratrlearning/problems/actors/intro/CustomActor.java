package com.iteratrlearning.problems.actors.intro;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;

public class CustomActor<T> implements Runnable {

    private final Queue<T> mailbox;
    private final BiConsumer<CustomActor<T>, T> actionHandler;
    private final BiConsumer<CustomActor<T>, Throwable> errorHandler;

    private CustomActor(BiConsumer<CustomActor<T>, T> behaviourHandler,
                        BiConsumer<CustomActor<T>, Throwable> errorHandler) {
        this.mailbox = new ConcurrentLinkedQueue<>();
        this.actionHandler = behaviourHandler;
        this.errorHandler = errorHandler;
    }

    static <T> CustomActor<T> create(BiConsumer<CustomActor<T>, T> behaviourHandler,
                                     BiConsumer<CustomActor<T>, Throwable> errorHandler) {
        return new CustomActor<>(behaviourHandler, errorHandler);
    }

    public void send(T message) {
        //TODO: add message to queue
    }

    @Override
    public void run() {
        // TODO: read and process message in mailbox
    }
}
