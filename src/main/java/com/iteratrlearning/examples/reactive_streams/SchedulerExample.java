package com.iteratrlearning.examples.reactive_streams;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class SchedulerExample
{
    public static void main(String[] args) throws InterruptedException
    {
        Flowable
            .range(1, 5)
            .map(SchedulerExample::printThread)
            .subscribeOn(Schedulers.computation())
            // .observeOn(Schedulers.computation())
            .subscribe(SchedulerExample::printValueAndThread);

        Thread.sleep(3000);
    }

    private static void printValueAndThread(final Object value)
    {
        System.out.println("Subscription onNext(): " + value +
            " on thread " + Thread.currentThread().getName());
    }

    private static Object printThread(final Object value)
    {
        System.out.println("Mapping " + value +
            " on thread " + Thread.currentThread().getName());

        return value;
    }
}
