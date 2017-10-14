package com.iteratrlearning.examples.reactive_streams;

public final class Track
{
    private final String name;
    private final int lengthInSeconds;
    private final String artist;

    public Track(final String name, final int lengthInSeconds, final String artist)
    {
        this.name = name;
        this.lengthInSeconds = lengthInSeconds;
        this.artist = artist;
    }

    public String getName()
    {
        return name;
    }

    public int getLengthInSeconds()
    {
        return lengthInSeconds;
    }

    public String getArtist()
    {
        return artist;
    }

    @Override
    public String toString()
    {
        return "Track{" +
            "name='" + name + '\'' +
            ", lengthInSeconds=" + lengthInSeconds +
            ", artist='" + artist + '\'' +
            '}';
    }
}
