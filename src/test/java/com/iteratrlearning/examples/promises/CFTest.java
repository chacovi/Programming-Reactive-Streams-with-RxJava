package com.iteratrlearning.examples.promises;

import com.iteratrlearning.examples.promises.pricefinder.Catalogue;
import com.iteratrlearning.examples.promises.pricefinder.Currency;
import com.iteratrlearning.examples.promises.pricefinder.Price;
import com.iteratrlearning.examples.promises.pricefinder.Product;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class CFTest {

    Catalogue catalogue = new Catalogue();
    AsyncPriceFinder priceFinder
            = new AsyncPriceFinder(Executors.newSingleThreadExecutor());

    @Test(timeout = 1_000)
    public void testCorrectPriceWithTestTimeout() {
        Product product = catalogue.productByName("Nexus7");
        CompletableFuture<Price> priceFuture = priceFinder.findBestPrice(product);

        Price expected = new Price(Currency.USD, 881.0);
        assertEquals(priceFuture.join(), expected);
    }

    @Test
    public void testCorrectPriceWithGetTimeout() throws Exception {
        Product product = catalogue.productByName("Nexus7");
        CompletableFuture<Price> priceFuture = priceFinder.findBestPrice(product);

        Price expected = new Price(Currency.USD, 881.0);
        assertEquals(priceFuture.get(1_000, TimeUnit.MILLISECONDS), expected);
    }

}
