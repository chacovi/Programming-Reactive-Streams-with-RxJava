package com.iteratrlearning.examples.reactive_streams;

import java.util.Arrays;
import java.util.List;

public class ThrottledTicksGraph
{
    private static final List<Character> TICKS = Arrays.asList(
        '\u2581','\u2582', '\u2583', '\u2584', '\u2585', '\u2586', '\u2587','\u2588');
    private static final int MAX_INDEX = TICKS.size() - 1;

    public static void main(String[] args)
    {
        ReactivePriceApiClient
            .pricesAtInterval()
            .map(Double::parseDouble)
            //.sample(500, TimeUnit.MILLISECONDS)
            //.buffer(5)
            //.map(numbers -> numbers.stream().mapToDouble(d -> d).average().getAsDouble())
            .blockingSubscribe(ThrottledTicksGraph::printPrice);
    }

    public static void printPrice(final double price)
    {
        final double min = 100;
        final int max = 200;
        final double range = max - min;
        final double normalisedPrice = (price - min) / range;
        final int index = (int) Math.round(normalisedPrice * MAX_INDEX);
        System.out.print(TICKS.get(index));
    }
}
