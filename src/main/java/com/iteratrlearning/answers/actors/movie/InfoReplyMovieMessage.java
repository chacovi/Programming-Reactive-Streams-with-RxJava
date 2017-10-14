package com.iteratrlearning.answers.actors.movie;

public final class InfoReplyMovieMessage {
    private final String movie;
    private final Integer views;

    public InfoReplyMovieMessage(String movie, int views) {
        this.movie = movie;
        this.views = views;
    }

    public String getMovie() {
        return movie;
    }

    public Integer getViews() {
        return views;
    }

    @Override
    public String toString() {
        return "InfoReplyMovieMessage{" +
                "movie='" + movie + '\'' +
                ", views=" + views +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfoReplyMovieMessage that = (InfoReplyMovieMessage) o;

        if (!getMovie().equals(that.getMovie())) return false;
        return getViews().equals(that.getViews());
    }

    @Override
    public int hashCode() {
        int result = getMovie().hashCode();
        result = 31 * result + getViews().hashCode();
        return result;
    }
}
