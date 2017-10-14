package com.iteratrlearning.answers.reactive_streams;

import com.iteratrlearning.examples.reactive_streams.Track;
import com.iteratrlearning.examples.reactive_streams.Tracks;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.junit.Test;

public class OperationsExercise
{

    @Test
    public void canFindAllArtistsWithTracksOver200Seconds()
    {
        final Flowable<String> names =
            Flowable.fromArray(Tracks.allTracks)
                .filter(track -> track.getLengthInSeconds() > 200)
                .map(Track::getArtist)
                .distinct();

        names.test().assertResult(Tracks.LED_ZEPPELIN, Tracks.THE_BEATLES);
    }

    @Test
    public void isThereATrackByPinkFloyd()
    {
        final Single<Boolean> names =
            Flowable.fromArray(Tracks.allTracks)
                .any(track -> track.getArtist().equals(Tracks.PINK_FLOYD));

        names.test().assertResult(true);
    }

    @Test
    public void canFindTheNameOfTheShortestTrackByLedZeppelin()
    {
        final Maybe<String> names =
            Flowable.fromArray(Tracks.allTracks)
                .filter(track -> track.getArtist().equals(Tracks.LED_ZEPPELIN))
                .reduce((acc, track) ->
                    track.getLengthInSeconds() < acc.getLengthInSeconds() ? track : acc)
                .map(Track::getName);

        names.test().assertResult("Rock and Roll");
    }
}
