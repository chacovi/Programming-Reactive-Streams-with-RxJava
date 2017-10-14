package com.iteratrlearning.answers.reactive_streams;

import io.reactivex.Flowable;
import org.apache.http.client.fluent.Request;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DownloadingWebPagesExercise
{

    @Test
    public void canDownloadWebPages() throws IOException
    {
        final String[] urls = {
            "http://iteratrlearning.com",
            "https://en.wikipedia.org/wiki/Bob_Dylan",
            "http://invaliddomainname.co.uk/"
        };

        // TODO: using the getContentAsString() method download the content of preceding urls
        // TODO: If there's an error downloading the page then return "Invalid URL" as the content
        final Flowable<String> content = Flowable
            .fromArray(urls)
            .map(this::getContentAsString)
            .onErrorReturnItem("Invalid URL");

        final List<String> values = content
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValueCount(3)
                .values();

        assertEquals(values.get(2), "Invalid URL");
    }

    private String getContentAsString(final String uri) throws IOException
    {
        return Request
            .Get(uri)
            .execute()
            .returnContent()
            .asString();
    }
}
