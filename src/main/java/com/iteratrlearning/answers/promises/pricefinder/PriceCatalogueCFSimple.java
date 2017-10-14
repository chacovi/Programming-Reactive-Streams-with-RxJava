package com.iteratrlearning.answers.promises.pricefinder;

import com.iteratrlearning.examples.promises.pricefinder.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.iteratrlearning.examples.promises.pricefinder.Currency.USD;

public class PriceCatalogueCFSimple
{
    private final Catalogue catalogue = new Catalogue();
    private final PriceFinder priceFinder = new PriceFinder();
    private final ExchangeService exchangeService = new ExchangeService();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args)
    {
        new PriceCatalogueCFSimple().findLocalDiscountedPrice(Currency.CHF, "Nexus7");
    }

    private void findLocalDiscountedPrice(final Currency localCurrency, final String productName)
    {
        long time = System.currentTimeMillis();

        CompletableFuture<Product> productFuture
                = CompletableFuture.supplyAsync(() -> catalogue.productByName(productName), executorService);

        CompletableFuture<Price> priceFuture
                = CompletableFuture.supplyAsync(() -> priceFinder.findBestPrice(productFuture.join()), executorService);

        CompletableFuture<Double> exchangeRateFuture
                = CompletableFuture.supplyAsync(() -> exchangeService.lookupExchangeRate(USD, localCurrency), executorService);

        double localPrice = exchange(priceFuture.join(), exchangeRateFuture.join());

        System.out.printf("A %s will cost us %f %s\n", productName, localPrice, localCurrency);
        System.out.printf("It took us %d ms to calculate this\n", System.currentTimeMillis() - time);
    }

    private double exchange(Price price, double exchangeRate)
    {
        return Utils.round(price.getAmount() * exchangeRate);
    }

}


