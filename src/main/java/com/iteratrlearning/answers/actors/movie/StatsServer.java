package com.iteratrlearning.answers.actors.movie;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.pattern.PatternsCS;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.util.Timeout;
import scala.concurrent.duration.Duration;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class StatsServer extends AllDirectives {

    private final ActorSystem actorSystem;
    private final ActorRef storageActor;

    public StatsServer() {
        actorSystem = ActorSystem.create();
        storageActor = actorSystem.actorOf(Props.create(StorageActor.class), "storage-actor");
    }

    public void run() throws IOException {
        final Http http = Http.get(actorSystem);
        final ActorMaterializer materializer = ActorMaterializer.create(actorSystem);

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow
                = this.createRoute().flow(actorSystem, materializer);
        final CompletionStage<ServerBinding> binding
                = http.bindAndHandle(routeFlow, ConnectHttp.toHost("localhost", 8080), materializer);

        System.out.println("Type RETURN to exit");
        System.in.read();

        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> actorSystem.terminate());
    }

    public static void main(String[] args) throws IOException {
        final StatsServer app = new StatsServer();
        app.run();
    }

    public Route createRoute() {
        // This handler generates responses to `/watch?movie=XXX` requests
        Route watchRoute =
                parameter("movie", movieName -> {
                    this.storageActor.tell(new ViewMovieMessage(movieName), null);
                    return complete("OK");
                });

        Route infoRoute =
                parameter("movie", movieName ->
                        onSuccess(() -> {
                            InfoMovieMessage msg = new InfoMovieMessage(movieName);
                            Timeout timeout = new Timeout(Duration.create(5, "seconds"));
                            CompletionStage<Object> ask = PatternsCS.ask(storageActor, msg, timeout);
                            return ask;
                        },
                                extraction -> complete(((InfoReplyMovieMessage)extraction).getViews().toString())
                                )
                );

        return
                // here the complete behavior for this server is defined
                get(() -> route(
                        // matches the empty path
                        pathSingleSlash(() ->
                                // return a constant string with a certain content type
                                complete(HttpEntities.create(ContentTypes.TEXT_HTML_UTF8,
                                        "<html><body>:-)</body></html>"))
                        ),
                        path("watch", () ->  watchRoute),
                        path("info", () -> infoRoute)));
    }
}