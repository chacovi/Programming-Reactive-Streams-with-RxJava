package com.iteratrlearning.answers.actors.intro;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

public class ActorSystem {

    private final static ExecutorService executorService = Executors.newCachedThreadPool();

    public static <T> CustomActor<T> spawn(BiConsumer<CustomActor<T>, T> behaviourHandler,
                                    BiConsumer<CustomActor<T>, Throwable> errorHandler) {

        CustomActor<T> actor = CustomActor.create(behaviourHandler, errorHandler);
        executorService.submit(actor);
        return actor;
    }

    public static void shutdown() {
        executorService.shutdown();
    }
}
