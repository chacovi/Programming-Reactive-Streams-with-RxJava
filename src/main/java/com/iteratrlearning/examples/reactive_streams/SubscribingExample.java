package com.iteratrlearning.examples.reactive_streams;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class SubscribingExample
{
    public static void main(String[] args)
    {
        // 1. Subscribe with a subscriber
        // 2. Print just the error message
        // 3. Complete not called with an error
        // 4. Subscribe with different Callbacks

        final Flowable<String> messages = Flowable.create(emitter ->
        {
            emitter.onNext("Hello");
            emitter.onNext("My name is Luke");
            emitter.onNext("I'm your Son");
            emitter.onComplete();
            //emitter.onError(new Exception("Noooooooooooooooo! I've lost an arm"));
        }, BackpressureStrategy.MISSING);

        messages.subscribe(new Subscriber<String>()
        {
            @Override
            public void onSubscribe(final Subscription subscription)
            {
                System.out.println("starting");
            }

            @Override
            public void onNext(final String message)
            {
                System.out.println(message);
            }

            @Override
            public void onError(final Throwable throwable)
            {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onComplete()
            {
                System.out.println("Complete");
            }
        });

        System.out.println("Done");
    }
}
