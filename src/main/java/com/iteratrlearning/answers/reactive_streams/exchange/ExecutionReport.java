package com.iteratrlearning.answers.reactive_streams.exchange;

public class ExecutionReport
{
    private final double price;
    private final String buyer;
    private final String seller;

    public ExecutionReport(final double price, final String buyer, final String seller)
    {
        this.price = price;
        this.buyer = buyer;
        this.seller = seller;
    }

    public String getSeller()
    {
        return seller;
    }

    public String getBuyer()
    {
        return buyer;
    }

    public double getPrice()
    {
        return price;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ExecutionReport that = (ExecutionReport) o;

        if (Double.compare(that.price, price) != 0) return false;
        if (buyer != null ? !buyer.equals(that.buyer) : that.buyer != null) return false;
        return seller != null ? seller.equals(that.seller) : that.seller == null;

    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        temp = Double.doubleToLongBits(price);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (buyer != null ? buyer.hashCode() : 0);
        result = 31 * result + (seller != null ? seller.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "ExecutionReport{" +
            "price=" + price +
            ", buyer='" + buyer + '\'' +
            ", seller='" + seller + '\'' +
            '}';
    }
}
