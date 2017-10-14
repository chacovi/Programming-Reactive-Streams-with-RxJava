package com.iteratrlearning.answers.actors.intro;

public class CustomActorRunner {

    private static CustomActor<String> pingActor;
    private static CustomActor<String> pongActor;

    public static void main(String[] args) throws InterruptedException {
        pingActor = ActorSystem.spawn((actor, message) -> {
                    if("PING".equals(message)) {
                            pongActor.send("PONG");
                    }
                    else if("STOP".equals(message)) {
                        System.exit(0);
                    }
                        System.out.println(message);
                }, (actor, exception) -> System.out.println(exception)
        );

        pongActor = ActorSystem.spawn((actor, message) -> {
                    if("PONG".equals(message)) {
                            pingActor.send("PING");
                    }
                    System.out.println(message);
                }, (actor, exception) -> System.out.println(exception)
        );

        pingActor.send("PING");

        Thread.sleep(10);
        pingActor.send("STOP");
        ActorSystem.shutdown();

    }
}
