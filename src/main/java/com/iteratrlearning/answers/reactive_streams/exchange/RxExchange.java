package com.iteratrlearning.answers.reactive_streams.exchange;

import com.iteratrlearning.answers.reactive_streams.exchange.Order.Side;
import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.function.Predicate;

import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingLong;

public class RxExchange
{
    private static final Comparator<Order> BY_TIMESTAMP = comparingLong(Order::getTimestamp);
    private static final Comparator<Order> PRICE_ASC = comparingDouble(Order::getPrice);
    private static final Comparator<Order> PRICE_DESC = PRICE_ASC.reversed();

    private final NavigableSet<Order> bids = new TreeSet<>(BY_TIMESTAMP);
    private final NavigableSet<Order> asks = new TreeSet<>(BY_TIMESTAMP);

    private final PublishProcessor<ExecutionReport> executionReports = PublishProcessor.create();
    private final PublishProcessor<MarketDataSnapshot> marketDataFeed = PublishProcessor.create();

    public Flowable<MarketDataSnapshot> marketDataFeed()
    {
        return marketDataFeed;
    }

    public Flowable<ExecutionReport> executionReports()
    {
        return executionReports;
    }

    public void connect(Flowable<Order> orders)
    {
        orders.subscribe(new Subscriber<Order>()
        {
            private Subscription subscription;
            private String party;

            @Override
            public void onSubscribe(final Subscription subscription)
            {
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(final Order order)
            {
                subscription.request(1);
                party = order.getParty();
                onOrder(order);
            }

            @Override
            public void onError(final Throwable t)
            {
                cancelOutstandingOrders(party);
            }

            @Override
            public void onComplete()
            {
                cancelOutstandingOrders(party);
            }
        });
    }

    public void onEndOfDay()
    {
        executionReports.onComplete();
        marketDataFeed.onComplete();
    }

    private void cancelOutstandingOrders(final String party)
    {
        if (party == null)
        {
            return;
        }

        Predicate<Order> partyMatches = order -> order.getParty().equals(party);
        bids.removeIf(partyMatches);
        asks.removeIf(partyMatches);
    }

    private double highestBid()
    {
        return maxPrice(bids, PRICE_ASC, Double.MIN_VALUE);
    }

    private double lowestAsk()
    {
        return maxPrice(asks, PRICE_DESC, Double.MAX_VALUE);
    }

    private Double maxPrice(
        final NavigableSet<Order> orders, final Comparator<Order> comparator, final double elseValue)
    {
        return orders.stream().max(comparator).map(Order::getPrice).orElse(elseValue);
    }

    public void onOrder(final Order order)
    {
        final Side side = order.getSide();
        final double price = order.getPrice();
        final NavigableSet<Order> otherSide;
        final Predicate<Order> matches;
        switch (side)
        {
            case BUY:
                otherSide = asks;
                matches = (sell) -> price >= sell.getPrice();
                break;

            case SELL:
                otherSide = bids;
                matches = (buy) -> buy.getPrice() >= price;
                break;

            default:
                throw new IllegalArgumentException();
        }

        final Iterator<Order> iterator = otherSide.iterator();
        while (iterator.hasNext())
        {
            final Order other = iterator.next();
            if (matches.test(other))
            {
                execute(order, side, other);

                iterator.remove();

                updateMarketDataFeed();
                return;
            }
        }

        addOrder(order);
    }

    private void updateMarketDataFeed()
    {
        marketDataFeed.onNext(new MarketDataSnapshot(highestBid(), lowestAsk()));
    }

    private void execute(
        final Order order, final Side side, final Order other)
    {
        final Order bid;
        final Order ask;
        if (side == Side.BUY)
        {
            bid = order;
            ask = other;
        }
        else
        {
            bid = other;
            ask = order;
        }

        final ExecutionReport executionReport = new ExecutionReport(
            ask.getPrice(), bid.getParty(), ask.getParty());

        executionReports.onNext(executionReport);
    }

    private void addOrder(final Order order)
    {
        switch (order.getSide())
        {
            case BUY:
                bids.add(order);
                if (bids.first() == order)
                {
                    updateMarketDataFeed();
                }
                break;

            case SELL:
                asks.add(order);
                if (asks.first() == order)
                {
                    updateMarketDataFeed();
                }
                break;
        }
    }
}
