package com.iteratrlearning.answers.reactive_streams.exchange;

public class MarketDataSnapshot
{
    private final double bid;
    private final double ask;
    private final long timestamp = System.nanoTime();

    public MarketDataSnapshot(final double bid, final double ask)
    {
        this.bid = bid;
        this.ask = ask;
    }

    public double getAsk()
    {
        return ask;
    }

    public double getBid()
    {
        return bid;
    }

    @Override
    public String toString()
    {
        return "MarketDataSnapshot{" +
            "bid=" + bid +
            ", ask=" + ask +
            ", timestamp=" + timestamp +
            '}';
    }

    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final MarketDataSnapshot that = (MarketDataSnapshot) o;

        if (Double.compare(that.bid, bid) != 0) return false;
        return Double.compare(that.ask, ask) == 0;
    }

    public int hashCode()
    {
        int result;
        long temp;
        temp = Double.doubleToLongBits(bid);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ask);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
