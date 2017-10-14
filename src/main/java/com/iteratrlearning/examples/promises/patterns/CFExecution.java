package com.iteratrlearning.examples.promises.patterns;

import java.util.concurrent.*;

public class CFExecution {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        long time = System.currentTimeMillis();

        CompletableFuture<String> future
                = CompletableFuture.supplyAsync(() -> delayedCallback("Hello"));

        future.thenAccept((message) -> delayedCallback(message+"1"));

        System.out.println("Hey from " + Thread.currentThread().getName());

        future.thenAccept((message) -> delayedCallback(message+"2"));


        Thread.sleep(7000);
    }

    public static String delayedCallback(String message) {
        uncheckedSleep(2000);
        System.out.println(message + " from " + Thread.currentThread().getName());
        return message;
    }

    public static void uncheckedSleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

