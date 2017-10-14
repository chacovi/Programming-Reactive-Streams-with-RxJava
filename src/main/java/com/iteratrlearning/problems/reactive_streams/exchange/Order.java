package com.iteratrlearning.problems.reactive_streams.exchange;

public class Order
{
    public enum Side
    { BUY, SELL }

    private final Side side;
    private final double price;
    private final String senderId;
    private final long timestamp = System.nanoTime();

    public Order(final Side side, final double price, final String senderId)
    {
        this.side = side;
        this.price = price;
        this.senderId = senderId;
    }

    public double getPrice()
    {
        return price;
    }

    public Side getSide()
    {
        return side;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public String getParty()
    {
        return senderId;
    }

    @Override
    public String toString()
    {
        return "Order{" +
            "side=" + side +
            ", price=" + price +
            ", senderId='" + senderId + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}
