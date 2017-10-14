package com.iteratrlearning.examples.promises.pricefinder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class AsyncExchangeServiceFaulty {

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public double exchange(double value, double rate)
    {
        return Utils.round(value * rate);
    }

    public CompletableFuture<Double> lookupExchangeRateAsync(Currency source, Currency destination) {
        CompletableFuture<Double> result = new CompletableFuture<>();
        if(Math.random() < 0.7) {
            result.completeExceptionally(new TimeoutException("Couldn't connect to the Exchange sorry!"));
        }

        executorService.submit(() -> {
            result.complete(getRateWithDelay(source, destination));
        });
        return result;
    }

    private double getRateWithDelay(Currency source, Currency destination) {
        Utils.randomDelay();
        return source.rate / destination.rate;
    }

}

