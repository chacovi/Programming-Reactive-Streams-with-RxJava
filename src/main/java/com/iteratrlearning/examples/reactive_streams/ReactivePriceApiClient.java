package com.iteratrlearning.examples.reactive_streams;

import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

public class ReactivePriceApiClient
{
    public static Flowable<String> pricesAtInterval()
    {
        return Flowable
            .interval(100, TimeUnit.MILLISECONDS)
            .map(x -> PriceApiService.getPrice());
    }

    public static Flowable<String> oneLazyPrice()
    {
        return Flowable
            .fromCallable(PriceApiService::getPrice);
    }

    public static Flowable<Double> testPrices()
    {
        return Flowable.just(100.0, 150.0, 200.0);
    }

    public static void main(String[] args)
    {
        // Reactive Streams on an interval
        pricesAtInterval()
            .blockingSubscribe(System.out::println);

        // Using a single value - like a CompletableFuture but composable
        /*oneLazyPrice()
            .blockingSubscribe(System.out::println);*/

        // Testing data
        /*testPrices()
            .subscribe(System.out::println);*/
    }
}
