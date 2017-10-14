package com.iteratrlearning.problems.reactive_streams;

import com.iteratrlearning.examples.reactive_streams.Track;
import com.iteratrlearning.examples.reactive_streams.Tracks;
import io.reactivex.Flowable;
import org.junit.Test;

public class TestingExercise
{
    @Test
    public void shouldContainLetItBeAndBeComplete()
    {
        final Flowable<Track> beatlesTracks = Flowable.fromArray(Tracks.allTracks)
            .filter(track -> track.getArtist().equals(Tracks.THE_BEATLES));

        // TODO: 1. Assert that beatlesTracks only value is Tracks.letItBe
        // TODO: 2. Assert that beatlesTracks has emitted the complete event
    }

    @Test
    public void shouldBeSubscribedAndContainFourValues()
    {
        final Flowable<Track> ledZeppelinTracks = Flowable.fromArray(Tracks.allTracks)
            .filter(track -> track.getArtist().equals(Tracks.LED_ZEPPELIN));

        ledZeppelinTracks.subscribe();

        // TODO: 1. Assert that ledZeppelinTracks has 4 values
        // TODO: 2. Assert that someone has subscribed to ledZeppelinTracks
    }
}
