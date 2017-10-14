package com.iteratrlearning.examples.reactive_streams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ObserverApiClient
{
    public static void main(String[] args)
    {
        final ObserverApiClient client = new ObserverApiClient();
        client.addObserver(System.out::println);
        client.start();
    }

    private final List<Consumer<String>> observers = new ArrayList<>();

    public void addObserver(final Consumer<String> observer)
    {
        observers.add(observer);
    }

    public void start()
    {
        while (true)
        {
            try
            {
                final String price = PriceApiService.getPrice();
                observers.forEach(observer -> observer.accept(price));

                Thread.sleep(1100);
            }
            catch (InterruptedException | IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
