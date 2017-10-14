package com.iteratrlearning.examples.reactive_streams.old;

import io.reactivex.Flowable;

import java.util.Arrays;
import java.util.List;

public class Example1CreatingFrom
{
    public static void main(String[] args)
    {
        final String value = "Hello World!";
        final String[] valuesArray = {"Hello", "My name is Luke", "I'm your Son"};
        final List<String> values = Arrays.asList(valuesArray);

        // 1. instantiate the Flowable from a value
        // 2. print out the results
        // 3. instantiate from an array
        // 4. instantiate from an iterable

        final Flowable<String> messages =
            Flowable.fromArray(valuesArray);

        messages
            .subscribe(System.out::println);
    }
}
