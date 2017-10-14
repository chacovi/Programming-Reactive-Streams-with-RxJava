package com.iteratrlearning.problems.promises.pricefinder;

import com.iteratrlearning.examples.promises.pricefinder.*;

import static com.iteratrlearning.examples.promises.pricefinder.Currency.USD;

public class PriceCatalogueCFSimple
{
    private final Catalogue catalogue = new Catalogue();
    private final PriceFinder priceFinder = new PriceFinder();
    private final ExchangeService exchangeService = new ExchangeService();

    public static void main(String[] args)
    {
        new PriceCatalogueCFSimple().findLocalDiscountedPrice(Currency.CHF, "Nexus7");
    }

    private void findLocalDiscountedPrice(final Currency localCurrency, final String productName)
    {
        long time = System.currentTimeMillis();

        // TODO: return a CompletableFuture
        Product product = catalogue.productByName(productName);

        // TODO: return a CompletableFuture
        Price price = priceFinder.findBestPrice(product);

        // TODO: return a CompletableFuture
        double exchangeRate = exchangeService.lookupExchangeRate(USD, localCurrency);

        // TODO: merge two CompletableFuture
        double localPrice = exchange(price, exchangeRate);

        System.out.printf("A %s will cost us %f %s\n", productName, localPrice, localCurrency);
        System.out.printf("It took us %d ms to calculate this\n", System.currentTimeMillis() - time);
    }

    private double exchange(Price price, double exchangeRate)
    {
        return Utils.round(price.getAmount() * exchangeRate);
    }

}

