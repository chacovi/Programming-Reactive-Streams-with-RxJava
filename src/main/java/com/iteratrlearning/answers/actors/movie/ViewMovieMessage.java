package com.iteratrlearning.answers.actors.movie;

public final class ViewMovieMessage {

    private final String movie;

    public ViewMovieMessage(String data) {
        this.movie = data;
    }

    public String getMovie() {
        return this.movie;
    }

    @Override
    public String toString() {
        return "ViewMessage{" +
                "movie='" + movie + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewMovieMessage that = (ViewMovieMessage) o;

        return getMovie().equals(that.getMovie());
    }

    @Override
    public int hashCode() {
        return getMovie().hashCode();
    }
}
