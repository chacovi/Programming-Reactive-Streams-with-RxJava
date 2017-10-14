package com.iteratrlearning.examples.reactive_streams;

public class StreamIdenticalOperations
{
    public static void main(String[] args)
    {
        // Filter, Map, Distinct
        // Find the names of tracks over 5 mins

        /*Flowable
            .fromArray(Tracks.allTracks)
            .filter(track -> track.getLengthInSeconds() > 300)
            .map(Track::getName)
            .subscribe(System.out::println);*/
    }

}
