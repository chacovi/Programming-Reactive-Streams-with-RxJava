package com.iteratrlearning.examples.promises;

import com.iteratrlearning.examples.promises.pricefinder.Currency;
import com.iteratrlearning.examples.promises.pricefinder.Price;
import com.iteratrlearning.examples.promises.pricefinder.Product;
import com.iteratrlearning.examples.promises.pricefinder.Utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class AsyncPriceFinder {

    private final Currency[] currencyValues;
    private final ExecutorService executorService;

    public AsyncPriceFinder(ExecutorService executorService) {
        this.currencyValues = Currency.values();
        this.executorService = executorService;
    }

    public CompletableFuture<Price> findBestPrice(Product product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product.getName()), executorService);
    }

    private Price calculatePrice(String product) {
        Utils.randomDelay();

        double price = 10 * product.charAt(0) + product.charAt(1);

        return new Price(pickRandomCurrencyForProduct(product), Utils.round(price));
    }

    private Currency pickRandomCurrencyForProduct(String product) {
        // hack to avoid using Random
        return currencyValues[product.charAt(2) % currencyValues.length];
    }

}
