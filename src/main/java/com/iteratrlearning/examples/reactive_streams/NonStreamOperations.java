package com.iteratrlearning.examples.reactive_streams;

import akka.stream.javadsl.Flow;
import io.reactivex.Flowable;

import java.util.ArrayList;
import java.util.List;

public class NonStreamOperations
{
    public static void main(String[] args)
    {
        // 1. check we have a track over 5 mins (all/allMatch, doOnNext/peek)
        // 2. contains Stairway to Heaven
        // 3. Total length of all tracks (reduce)
        // 4. create a list of the values (collect)
        // 5. Dot product of two Flowables (zip)

        final Flowable<Integer> left = Flowable.fromArray(1, 2, 3);
        final Flowable<Integer> right = Flowable.fromArray(4, -5, 6);

        /*Flowable
            .fromArray(Tracks.allTracks)
            .doOnNext(System.out::println)
            .all(track -> track.getLengthInSeconds() > 300)
            .subscribe(System.out::println);

        System.out.println();

        Flowable
            .fromArray(Tracks.allTracks)
            .contains(Tracks.stairwayToHeaven)
            .subscribe(System.out::println);

        System.out.println();

        Flowable
            .fromArray(Tracks.allTracks)
            .reduce(0, (acc, track) -> acc + track.getLengthInSeconds())
            .subscribe(System.out::println);

        System.out.println();

        Flowable
            .fromArray(Tracks.allTracks)
            .collect(ArrayList::new, ArrayList::add)
            .subscribe(System.out::println);

        System.out.println();

        final Flowable<Integer> left = Flowable.fromArray(1, 2, 3);
        final Flowable<Integer> right = Flowable.fromArray(4, -5, 6);

        left.zipWith(right, (x, y) -> x * y)
            .reduce(0, Integer::sum)
            .subscribe(System.out::println);*/
    }
}
