package com.iteratrlearning.problems.reactive_streams.exchange;

import com.iteratrlearning.problems.reactive_streams.exchange.Order.Side;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Ignore;
import org.junit.Test;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.MIN_VALUE;

@Ignore // Remove Ignore and fix tests
public class RxExchangeTest
{
    private static final String FIRST_SELLER = "seller1";
    private static final String FIRST_BUYER = "buyer1";
    private static final String SECOND_SELLER = "seller2";
    private static final String SECOND_BUYER = "buyer2";
    private static final double PRICE = 1.0;

    private RxExchange exchange = new RxExchange();
    private TestSubscriber<ExecutionReport> reports = exchange.executionReports().test();
    private TestSubscriber<MarketDataSnapshot> marketData = exchange.marketDataFeed().test();

    @Test
    public void shouldMatchSellAndBuyOrder()
    {
        firstSell();
        firstBuy();

        hit();
    }

    @Test
    public void shouldMatchBuyAndSellOrder()
    {
        firstBuy();
        firstSell();

        hit();
    }

    @Test
    public void shouldMatchFirstBuyAndSellOrder()
    {
        firstBuy();
        secondBuy();
        firstSell();

        hit();
    }

    @Test
    public void shouldMatchFirstSellAndBuyOrder()
    {
        firstSell();
        secondSell();
        firstBuy();

        hit();
    }

    @Test
    public void shouldNotifyEndOfDay()
    {
        exchange.onEndOfDay();

        reports.assertComplete();
        marketData.assertComplete();
    }

    @Test
    public void shouldEmitMarketDataForBuyer()
    {
        firstBuy();

        marketData.assertValues(new MarketDataSnapshot(PRICE, MAX_VALUE))
                  .assertNotTerminated();
    }

    @Test
    public void shouldEmitMarketDataForSeller()
    {
        firstSell();

        marketData.assertValues(new MarketDataSnapshot(MIN_VALUE, PRICE))
                  .assertNotTerminated();
    }

    @Test
    public void shouldEmitMarketDataForHitAndFurtherOrders()
    {
        firstBuy();
        firstSell();
        firstBuy();

        marketData.assertValues(
                    new MarketDataSnapshot(PRICE, MAX_VALUE),
                    new MarketDataSnapshot(MIN_VALUE, MAX_VALUE),
                    new MarketDataSnapshot(PRICE, MAX_VALUE))
                  .assertNotTerminated();
    }

    private void firstBuy()
    {
        buy(FIRST_BUYER);
    }

    private void secondBuy()
    {
        buy(SECOND_BUYER);
    }

    private void buy(final String buyer)
    {
        exchange.onOrder(new Order(Side.BUY, PRICE, buyer));
    }

    private void firstSell()
    {
        sell(FIRST_SELLER);
    }

    private void secondSell()
    {
        sell(SECOND_SELLER);
    }

    private void sell(final String seller)
    {
        exchange.onOrder(new Order(Side.SELL, PRICE, seller));
    }

    private void hit()
    {
        reports.assertValues(new ExecutionReport(PRICE, FIRST_BUYER, FIRST_SELLER))
               .assertNotTerminated();
    }
}
