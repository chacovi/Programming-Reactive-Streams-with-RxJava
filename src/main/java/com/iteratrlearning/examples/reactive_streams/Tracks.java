package com.iteratrlearning.examples.reactive_streams;

public class Tracks
{

    public static final String LED_ZEPPELIN = "Led Zeppelin";
    public static final String PINK_FLOYD = "Pink Floyd";
    public static final String THE_BEATLES = "The Beatles";

    public static final Track blackDog =
        new Track("Black Dog", 294, LED_ZEPPELIN);
    public static final Track rockAndRoll =
        new Track("Rock and Roll", 220, LED_ZEPPELIN);
    public static final Track theBattleOfEvermore =
        new Track("The Battle of Evermore", 351, LED_ZEPPELIN);
    public static final Track stairwayToHeaven =
        new Track("Stairway to Heaven", 482, LED_ZEPPELIN);

    public static final Track inTheFlesh =
        new Track("In the Flesh?", 196, PINK_FLOYD);

    public static final Track letItBe =
        new Track("Let It Be", 243, THE_BEATLES);

    public static final Track[] allTracks =
        { blackDog, rockAndRoll, theBattleOfEvermore, stairwayToHeaven, inTheFlesh, letItBe };
}
