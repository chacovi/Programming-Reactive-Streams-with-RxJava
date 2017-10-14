package com.iteratrlearning.examples.reactive_streams;

import io.reactivex.Flowable;
import io.reactivex.processors.ReplayProcessor;
import io.reactivex.processors.UnicastProcessor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class ProcessorExample
{
    public static void main(String[] args)
    {
        // 1. Explain Luke
        // 2. Wire up Darth and Luke
        // 3. Implement Darth
        // 4. Use In Chat Room

        /*DarthVader darthVader = new DarthVader();
        darthVader.outgoing().subscribe(System.out::println);
        Luke luke = new Luke();
        luke.outgoing()
            .doOnNext(System.out::println)
            .subscribe(darthVader.incoming());*/

        // startChatRoom(new Luke(), new DarthVader());
    }

    private interface ChatBot
    {
        Flowable<String> outgoing();

        Subscriber<String> incoming();
    }

    private static class DarthVader implements ChatBot
    {
        private final UnicastProcessor<String> processor = UnicastProcessor.create();

        private List<String> onMessage(final String message)
        {
            switch (message)
            {
                case "I'll never join you!":
                    return singletonList("If you only knew the power of The Dark Side! Obi-Wan never told you what happened to your father.");

                case "He told me enough! He told me you killed him!":
                    return singletonList("No. I am your father.");
            }

            return emptyList();
        }

        public Flowable<String> outgoing()
        {
            return processor.flatMapIterable(this::onMessage);
        }

        public Subscriber<String> incoming()
        {
            return processor;
        }
    }

    private static class Luke implements ChatBot
    {
        public Flowable<String> outgoing()
        {
            return Flowable.just(
                "I'll never join you!",
                "He told me enough! He told me you killed him!");
        }

        public Subscriber<String> incoming()
        {
            return new Subscriber<String>()
            {
                public void onSubscribe(final Subscription subscription)
                {
                }

                public void onNext(final String s)
                {
                }

                public void onError(final Throwable throwable)
                {
                }

                public void onComplete()
                {
                }
            };
        }
    }

    private static void startChatRoom(final ChatBot ... bots)
    {
        final ReplayProcessor<String> chatRoom = ReplayProcessor.create();
        chatRoom.subscribe(System.out::println);
        Stream.of(bots).forEach(bot -> chatRoom.subscribe(bot.incoming()));
        final List<ChatBot> reverse = Arrays.asList(bots);
        Collections.reverse(reverse);
        reverse.forEach(bot -> bot.outgoing().subscribe(chatRoom));
    }
}
