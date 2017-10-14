package com.iteratrlearning.problems.promises.pricefinder;

import com.iteratrlearning.examples.promises.pricefinder.*;

import static com.iteratrlearning.examples.promises.pricefinder.Currency.USD;

public class PriceCatalogueThread
{
    private final Catalogue catalogue = new Catalogue();
    private final PriceFinder priceFinder = new PriceFinder();
    private final ExchangeService exchangeService = new ExchangeService();

    public static void main(String[] args) throws InterruptedException {
        new PriceCatalogueThread().findLocalDiscountedPrice(Currency.CHF, "Nexus7");
    }

    private void findLocalDiscountedPrice(final Currency localCurrency, final String productName)
    {
        long time = System.currentTimeMillis();

        // TODO: Wrap up in Runnable
        Product product = catalogue.productByName(productName);
        Price price = priceFinder.findBestPrice(product);

        // TODO: Wrap up in Runnable
        double exchangeRate = exchangeService.lookupExchangeRate(USD, localCurrency);

        // TODO: start threads
        // TODO: join threads

        // TODO: merge two results
        double localPrice = exchange(price, exchangeRate);

        System.out.printf("A %s will cost us %f %s\n", productName, localPrice, localCurrency);
        System.out.printf("It took us %d ms to calculate this\n", System.currentTimeMillis() - time);
    }

    private double exchange(Price price, double exchangeRate)
    {
        return Utils.round(price.getAmount() * exchangeRate);
    }

}
