package com.iteratrlearning.problems.reactive_streams;

import io.reactivex.Flowable;

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
        // TODO: subscribe or blockingSubscribe to this news feed
        final Flowable<String> newsFeed = Flowable.fromArray(NEWS_TITLES);

        // TODO: If a user enters a digit, print that number of news items, use the reactive pull back pressure mechanism
        // To pull items out of the newsFeed Flowable.
        // TODO: If a user anything else ignore it
        // TODO: When there is no more news. print on the commandline "No More News"

        // Hint: use reactive-pull back pressure to control the flow of news items
        // Hint: System.in.read() to read a digit and Character.getNumericValue() to convert it to a number
    }
}
