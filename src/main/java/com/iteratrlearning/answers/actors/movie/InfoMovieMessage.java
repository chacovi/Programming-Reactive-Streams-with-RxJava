package com.iteratrlearning.answers.actors.movie;

public final class InfoMovieMessage {

    private final String movie;

    public InfoMovieMessage(String data) {
        this.movie = data;
    }

    public String getMovie() {
        return this.movie;
    }

    @Override
    public String toString() {
        return "InfoMovieMessage{" +
                "movie='" + movie + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfoMovieMessage that = (InfoMovieMessage) o;

        return getMovie().equals(that.getMovie());
    }

    @Override
    public int hashCode() {
        return getMovie().hashCode();
    }
}
