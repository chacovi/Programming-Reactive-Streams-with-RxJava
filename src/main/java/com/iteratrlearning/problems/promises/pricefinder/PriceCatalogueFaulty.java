package com.iteratrlearning.problems.promises.pricefinder;

import com.iteratrlearning.examples.promises.pricefinder.*;

import java.util.concurrent.CompletableFuture;

import static com.iteratrlearning.examples.promises.pricefinder.Currency.USD;

public class PriceCatalogueFaulty {
        private final Catalogue catalogue = new Catalogue();
        private final PriceFinder priceFinder = new PriceFinder();
        private final AsyncExchangeServiceFaulty exchangeService = new AsyncExchangeServiceFaulty();

        public static void main(String[] args) throws InterruptedException {
            new PriceCatalogueFaulty().findLocalDiscountedPrice(Currency.CHF, "Nexus7");
        }

        private void findLocalDiscountedPrice(final Currency localCurrency, final String productName)
        {

            try {
                long time = System.currentTimeMillis();

                Product product = catalogue.productByName(productName);
                Price price = priceFinder.findBestPrice(product);

                CompletableFuture<Double> exchangeRate = exchangeService.lookupExchangeRateAsync(USD, localCurrency);

                double localPrice = exchange(price, exchangeRate.join());
                System.out.printf("A %s will cost us %f %s\n", productName, localPrice, localCurrency);
                System.out.printf("It took us %d ms to calculate this\n", System.currentTimeMillis() - time);
            } catch (Exception e) {
                System.out.println("Sorry try again next time: " + e.getCause().getMessage());
            }
        }

        private double exchange(Price price, double exchangeRate)
        {
            return Utils.round(price.getAmount() * exchangeRate);
        }

}
