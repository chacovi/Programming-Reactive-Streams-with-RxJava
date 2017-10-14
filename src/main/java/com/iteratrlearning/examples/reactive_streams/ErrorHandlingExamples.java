package com.iteratrlearning.examples.reactive_streams;

import io.reactivex.Flowable;

public class ErrorHandlingExamples
{
    public static void main(String[] args)
    {
        // 1. recovery from div by zero, onErrorResumeNext() and onErrorReturn()
        // 2. error propagation through a pipeline

        final Flowable<Integer> betterNumbers =
            Flowable.fromArray(4, 5, 6);

        // Recovery:
        int numerator = 2;
        Flowable.fromArray(3, 2, 1, 0)
                .map(x -> numerator / x)
                //.onErrorReturn(ex -> 0)
                .onErrorReturnItem(1)
                //.onErrorResumeNext(betterNumbers)
                .blockingSubscribe(
                    System.out::println,
                    System.err::println);
    }
}
