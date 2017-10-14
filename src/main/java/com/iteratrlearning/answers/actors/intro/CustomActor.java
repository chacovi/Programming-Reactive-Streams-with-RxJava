package com.iteratrlearning.answers.actors.intro;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;

public class CustomActor<T> implements Runnable {

    private final ConcurrentLinkedQueue<T> mailbox;
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
        mailbox.offer(message);
    }

    @Override
    public void run() {
        try {
            while(true) {
                T message = mailbox.poll();
                if(message != null) {
                    actionHandler.accept(this, message);
                }
            }
        } catch(Exception e) {
            errorHandler.accept(this, e);
        }
    }
}
