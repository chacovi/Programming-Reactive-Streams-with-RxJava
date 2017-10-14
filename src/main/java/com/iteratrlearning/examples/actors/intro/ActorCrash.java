package com.iteratrlearning.examples.actors.intro;

import com.iteratrlearning.answers.actors.intro.ActorSystem;
import com.iteratrlearning.answers.actors.intro.CustomActor;

import java.util.zip.CRC32;

// Demo with: -Xmx50M -XX:+PrintGC -XX:+PrintGCDetails
public class ActorCrash
{
    private static final CRC32 crc32 = new CRC32();
    private static final long EXPECTED_CHECKSUM = calcChecksum("Hello");

    public static void main(String[] args)
    {
        CustomActor<Message> basicActor = ActorSystem.spawn((actor, message) ->
            {
                final String content = message.getContent();
                if ("STOP".equals(content) || calcChecksum(content) != EXPECTED_CHECKSUM)
                {
                    System.exit(0);
                }
            }, (actor, exception) -> System.out.println(exception)
        );

        boolean flag = true;
        while (flag)
        {
            basicActor.send(new Message("Hello"));
        }

        ActorSystem.shutdown();
    }

    private static long calcChecksum(final String content)
    {
        final byte[] bytes = content.getBytes();
        crc32.update(bytes);
        final long checksum = crc32.getValue();
        crc32.reset();
        return checksum;
    }

    private static class Message
    {
        private final String content;

        private Message(String content)
        {
            this.content = content;
        }

        public String getContent()
        {
            return content;
        }
    }
}
