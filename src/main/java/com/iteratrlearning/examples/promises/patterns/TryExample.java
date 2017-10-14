package com.iteratrlearning.examples.promises.patterns;

import javaslang.control.Try;

import java.util.concurrent.CompletableFuture;

public class TryExample {

    public static void main(String[] args) {

        new TryExample().runWithTry();

    }

    public static void runWithTry() {
        // 1) Try.of + methods
        // 2) CompletableFuture + Try + findPrice | CF.thenApply + Try.map
        // 3) Try.of with exception, onFailure
        // 4) CF + Try + buggyFindPrice + getOrElse
        CompletableFuture<Try<Double>> futureTryPrice
                = CompletableFuture.supplyAsync(() -> Try.of(() -> findPrice("Nexus7")));

        futureTryPrice.thenApply(tryPrice -> tryPrice.map(price -> price * 2).getOrElse(0.0))
                .thenAccept(System.out::println)
                .join();
    }


    public static double findPrice(String productName) {
        return 42.0;
    }

    public static double buggyFindPrice(String productName) {
        throw new RuntimeException("Boom");
    }

}
