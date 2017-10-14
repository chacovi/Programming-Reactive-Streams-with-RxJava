package com.iteratrlearning.answers.promises.pricefinder;

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

    private void findLocalDiscountedPrice(final Currency localCurrency, final String productName) throws InterruptedException {
        long time = System.currentTimeMillis();

        PriceRunnable priceRunnable = new PriceRunnable(productName);
        ExchangeRunnable exchangeRunnable = new ExchangeRunnable(localCurrency);

        Thread priceThread = new Thread(priceRunnable);
        priceThread.start();

        Thread exchangeThread = new Thread(exchangeRunnable);
        exchangeThread.start();

        priceThread.join();
        exchangeThread.join();

        double localPrice = exchange(priceRunnable.getPrice(), exchangeRunnable.getResult());

        System.out.printf("A %s will cost us %f %s\n", productName, localPrice, localCurrency);
        System.out.printf("It took us %d ms to calculate this\n", System.currentTimeMillis() - time);
    }

    private double exchange(Price price, double exchangeRate)
    {
        return Utils.round(price.getAmount() * exchangeRate);
    }

    class PriceRunnable implements Runnable {

        private final String productName;
        private Price price;

        public PriceRunnable(String productName) {
            this.productName = productName;
        }

        @Override
        public void run() {
            Product product = catalogue.productByName(productName);
            Price price = priceFinder.findBestPrice(product);
            this.price = price;
        }

        public Price getPrice() {
            return this.price;
        }
    }

    class ExchangeRunnable implements Runnable {

        private final Currency localCurrency;
        private double result;

        ExchangeRunnable(Currency localCurrency) {
            this.localCurrency = localCurrency;
        }

        @Override
        public void run() {
            double result = exchangeService.lookupExchangeRate(USD, localCurrency);
            this.result = result;
        }

        public double getResult() {
            return result;
        }
    }

}
