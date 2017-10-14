package com.iteratrlearning.examples.synchronous.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ServiceProxy
{
    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T get(final Class<T> cls, final URIBuilder uriBuilder) throws IOException, URISyntaxException
    {
        final URI uri = uriBuilder.build();
        final Response response = Request.Get(uri).execute();
        return mapper.readValue(
            response.returnContent().asStream(), cls);
    }

    public String localhost(final int port, final String suffix)
    {
        return String.format("http://localhost:%d%s", port, suffix);
    }
}
