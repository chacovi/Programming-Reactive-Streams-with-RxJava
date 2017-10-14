package com.iteratrlearning.problems.actors.intro;

public class CustomActorRunner {

    private static CustomActor<String> pingActor;
    private static CustomActor<String> pongActor;

    public static void main(String[] args) throws InterruptedException {

        // TODO: Part 1 - create an actor which replies "World" to "Hello"
        // TODO: Part 2 - when receiving "STOP" the actors should exit

        ActorSystem.shutdown();

    }
}
