package com.iteratrlearning.answers.reactive_streams;

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

        beatlesTracks.test()
                     .assertValues(Tracks.letItBe)
                     .assertComplete();
    }

    @Test
    public void shouldBeSubscribedAndContainFourValues()
    {
        final Flowable<Track> ledZeppelinTracks = Flowable.fromArray(Tracks.allTracks)
            .filter(track -> track.getArtist().equals(Tracks.LED_ZEPPELIN));

        ledZeppelinTracks.subscribe();

        ledZeppelinTracks.test()
            .assertValueCount(4)
            .assertSubscribed();
    }
}
