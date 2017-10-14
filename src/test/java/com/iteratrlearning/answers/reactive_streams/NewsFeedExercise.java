package com.iteratrlearning.answers.reactive_streams;

import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;

public class NewsFeedExercise
{
    private static final String[] NEWS_TITLES =
        {
            "Trump election: US 'identifies agents behind Russian hack'",
            "Syria conflict: Russia 'starts to reduce forces'",
            "Huge Antarctic iceberg poised to break away",
            "Sound of 2017: Unsigned soul singer Ray BLK wins",
            "M62 police shooting: Yassar Yaqub funeral held",
            "Stonehenge: Hidden sounds of prehistoric site revealed on new app",
            "Sturgeon suggests soft Brexit could take indyref off table in short term",
            "David Bowie terminal cancer diagnosis 'three months before death'"
        };

    public static void main(String[] args)
    {
        Flowable.fromArray(NEWS_TITLES)
                .blockingSubscribe(new Subscriber<String>()
        {
            private int numberOfItems;
            private Subscription subscription;

            @Override
            public void onSubscribe(final Subscription subscription)
            {
                this.subscription = subscription;
                waitForInput();
            }

            @Override
            public void onNext(final String newsItem)
            {
                System.out.println(newsItem);
                numberOfItems--;

                if (numberOfItems == 0)
                {
                    waitForInput();
                }
            }

            @Override
            public void onError(final Throwable t)
            {
                t.printStackTrace();
            }

            @Override
            public void onComplete()
            {
                System.out.println("No More News");
            }

            private void waitForInput()
            {
                try
                {
                    while (true)
                    {
                        char requestedItems = (char) System.in.read();
                        numberOfItems = Character.getNumericValue(requestedItems);
                        if (numberOfItems > 0 && numberOfItems <= 9)
                        {
                            subscription.request(numberOfItems);
                            break;
                        }
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
