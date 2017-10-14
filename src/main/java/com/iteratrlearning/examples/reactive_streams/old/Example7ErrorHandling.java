package com.iteratrlearning.examples.reactive_streams.old;

import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

public class Example7ErrorHandling
{
    public static void main(String[] args)
    {
        // 1. retry a transient failure

        Flowable
            .interval(100, TimeUnit.MILLISECONDS)
            .map(input -> {
                if (Math.random() < .5) {
                    throw new RuntimeException();
                }
                return "Success " + input;
            })
            .retry()
            .blockingSubscribe(System.out::println);
    }
}
