package com.iteratrlearning.examples.reactive_streams;

import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

// Finding prices that are over 150
public class OperationsPriceApiClient
{
    public static Flowable<String> pricesAtInterval()
    {
        return Flowable
            .interval(100, TimeUnit.MILLISECONDS)
            .map(x -> PriceApiService.getPrice());
    }

    public static void main(String[] args)
    {
        pricesAtInterval()
            .map(Double::parseDouble)
            .filter(price -> price > 150)
            .blockingSubscribe(System.out::println, Throwable::printStackTrace);
    }
}
