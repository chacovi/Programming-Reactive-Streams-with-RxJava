package com.iteratrlearning.examples.reactive_streams.old;

import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

public class Example9BackPressure
{
    public static void main(String[] args)
    {
        // 1. reactive-pull back pressure in a custom subscriber
        // 2. zip intervals of 500 + 200 ms, to show buffering in zip
        // 3. zip intervals of 5 + 2 ms, to show back pressure fail
        // 4. onBackPressureBuffer/onBackPressureDrop to zip example
        // 5. Note the overloads of create

        Flowable
            .range(1, 100)
            .subscribe(new Subscriber<Integer>()
            {
                private Subscription subscription;

                @Override
                public void onSubscribe(final Subscription subscription)
                {
                    this.subscription = subscription;
                    subscription.request(1);
                }

                @Override
                public void onNext(final Integer integer)
                {
                    System.out.println(integer);
                    subscription.request(1);
                }

                @Override
                public void onError(final Throwable throwable)
                {

                }

                @Override
                public void onComplete()
                {
                    System.out.println("complete");
                }
            });

        Flowable
            .interval(2, TimeUnit.MILLISECONDS)
            .onBackpressureDrop()
            .zipWith(Flowable.interval(5, TimeUnit.MILLISECONDS), Long::sum)
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
