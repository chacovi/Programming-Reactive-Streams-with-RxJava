package com.iteratrlearning.examples.reactive_streams.old;

import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

public class Example8Throttling
{
    public static void main(String[] args)
    {
        // 1. Print out interval of 50 ms for 10 seconds
        // 2. throttleLast (sample) and throttleFirst the stream @ 240 ms
        // 3. Print out burst of 3 numbers every 500 ms (bus driver problem)
        // 4. use throttleWithTimeout (debounce) to flatten bursts @ 100 ms
        // 5. use buffer to collect values @ 3 and 100 ms

        Flowable
            .interval(50, TimeUnit.MILLISECONDS)
            .sample(240, TimeUnit.MILLISECONDS)
            .subscribe(System.out::println);

        Flowable
            .interval(500, TimeUnit.MILLISECONDS)
            .flatMap(x -> Flowable.fromArray(x, 10 * x, 2 * x))
            .throttleWithTimeout(100, TimeUnit.MILLISECONDS)
            .subscribe(System.out::println);

        Flowable
            .interval(500, TimeUnit.MILLISECONDS)
            .flatMap(x -> Flowable.fromArray(x, 10 * x, 2 * x))
            .buffer(3)
            .buffer(100, TimeUnit.MILLISECONDS)
            .subscribe(System.out::println);

        wait10Seconds();
    }

    private static void wait10Seconds()
    {
        try
        {
            Thread.sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
