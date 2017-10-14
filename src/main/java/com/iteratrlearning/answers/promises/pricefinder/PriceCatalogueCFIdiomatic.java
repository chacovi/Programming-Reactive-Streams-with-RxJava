package com.iteratrlearning.answers.promises.pricefinder;

import com.iteratrlearning.examples.promises.pricefinder.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PriceCatalogueCFIdiomatic
{
    private static final Catalogue catalogue = new Catalogue();
    private static final PriceFinder priceFinder = new PriceFinder();
    private static final ExchangeService exchangeService = new ExchangeService();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException
    {
        new PriceCatalogueCFIdiomatic().findLocalDiscountedPrice(Currency.CHF, "Nexus7");
    }

    private void findLocalDiscountedPrice(final Currency localCurrency, final String productName) throws InterruptedException
    {
        long time = System.currentTimeMillis();

        lookupProductByName(productName)

                .thenComposeAsync(this::findBestPrice, executorService)

                .thenCombineAsync(lookupExchangeRate(localCurrency), this::exchange, executorService)

                .thenAcceptAsync(localAmount ->
                {
                    System.out.printf("A %s will cost us %f %s\n", productName, localAmount, localCurrency);
                    System.out.printf("It took us %d ms to calculate this\n", System.currentTimeMillis() - time);
                }, executorService).join();
    }

    private CompletableFuture<Price> findBestPrice(Product product)
    {
        return CompletableFuture.supplyAsync(() -> priceFinder.findBestPrice(product));
    }

    private double exchange(Price price, double exchangeRate)
    {
        return Utils.round(price.getAmount() * exchangeRate);
    }

    private CompletableFuture<Product> lookupProductByName(String productName)
    {
        return CompletableFuture.supplyAsync(() -> catalogue.productByName(productName), executorService);
    }

    private CompletableFuture<Double> lookupExchangeRate(Currency localCurrency)
    {
        return CompletableFuture.supplyAsync(() ->
                exchangeService.lookupExchangeRate(Currency.USD, localCurrency), executorService);
    }

}
