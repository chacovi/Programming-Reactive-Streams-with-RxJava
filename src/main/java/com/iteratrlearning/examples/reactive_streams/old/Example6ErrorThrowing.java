package com.iteratrlearning.examples.reactive_streams.old;

import io.reactivex.Flowable;

public class Example6ErrorThrowing
{
    public static void main(String[] args)
    {
        // 0. subscribe with out and err prints.
        // 1. throw runtime exception
        // 2. throw checked exception

        int numerator = 2;
        Flowable.fromArray(3, 2, 1, 0)
                .map(x -> numerator / x)
                .subscribe(
                    System.out::println,
                    System.err::println);

        Flowable.fromArray(3, 2, 1, 0)
            .map(x -> { throw new Exception("OHS NOES!"); })
            .subscribe(
                System.out::println,
                System.err::println);
    }
}
