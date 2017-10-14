package com.iteratrlearning.examples.reactive_streams;

import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;

/**
 * .
 */
public class BackpressuredTicksGraph
{

    private static Subscriber<Double> backPressuredTicksGraph()
    {
        return new Subscriber<Double>()
        {
            private Subscription s;

            @Override
            public void onSubscribe(final Subscription s)
            {
                this.s = s;
                s.request(1);
            }

            @Override
            public void onNext(final Double price)
            {
                ThrottledTicksGraph.printPrice(price);

                try
                {
                    Thread.sleep(500);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                s.request(1);
            }

            @Override
            public void onError(final Throwable t)
            {
                t.printStackTrace();
            }

            @Override
            public void onComplete()
            {
                System.out.println("Done");
            }
        };
    }

    private static Publisher<String> pricePublisher()
    {
        return (subscriber) ->
        {
            subscriber.onSubscribe(new Subscription()
            {
                @Override
                public void request(final long n)
                {
                    for (long i = 0; i < n; i++)
                    {
                        try
                        {
                            subscriber.onNext(PriceApiService.getPrice());
                        }
                        catch (final IOException e)
                        {
                            subscriber.onError(e);
                        }
                    }
                }

                @Override
                public void cancel()
                {
                    subscriber.onComplete();
                }
            });
        };
    }

    public static void main(String[] args)
    {
        Flowable.fromPublisher(pricePublisher())
            .map(Double::parseDouble)
            .subscribe(backPressuredTicksGraph());
    }
}
