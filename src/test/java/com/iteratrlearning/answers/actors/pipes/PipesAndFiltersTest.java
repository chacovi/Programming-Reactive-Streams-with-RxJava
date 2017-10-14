package com.iteratrlearning.answers.actors.pipes;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static akka.testkit.JavaTestKit.duration;

public class PipesAndFiltersTest {

    private ActorSystem system;

    @Test
    public void testTextCheckerAndLatextoUnicodeAndBase64EncoderPipeline() {
        JavaTestKit endProbe = new JavaTestKit(system);
        // LatexToUnicode filter
        Props base54EncoderProps = Props.create(Base64EncoderActor.class, endProbe.getRef());
        ActorRef base64EncoderActor = system.actorOf(base54EncoderProps, "base64-encoder-actor");
        // LatexToUnicode filter
        Props latexToUnicodeProps = Props.create(LatexToUnicodeActor.class, base64EncoderActor);
        ActorRef latexToUnicodeActor = system.actorOf(latexToUnicodeProps, "latex-to-unicode-actor");
        // TextChecker filter
        Props textCheckerProps = Props.create(TextCheckerActor.class, latexToUnicodeActor);
        ActorRef textCheckerActor = system.actorOf(textCheckerProps, "text-checker-actor");
        // test
        textCheckerActor.tell("I think the answer is \\alpha + \\beta", textCheckerActor);
        String expectedResult = "SSB0aGluayB0aGUgYW5zd2VyIGlzIM6xICsgzrI=";
        endProbe.expectMsgEquals(duration("1 second"), expectedResult);
    }

    @Test
    public void testTextCheckerAndNoLatextoUnicodePipeline() {
        JavaTestKit endProbe = new JavaTestKit(system);
        // LatexToUnicode filter
        Props latexToUnicodeProps = Props.create(LatexToUnicodeActor.class, endProbe.getRef());
        ActorRef latexToUnicodeActor = system.actorOf(latexToUnicodeProps, "latex-to-unicode-actor");
        // TextChecker filter
        Props textCheckerProps = Props.create(TextCheckerActor.class, latexToUnicodeActor);
        ActorRef textCheckerActor = system.actorOf(textCheckerProps, "text-checker-actor");
        // test
        textCheckerActor.tell("I am feeling \\alpha", textCheckerActor);
        endProbe.expectMsgEquals(duration("1 second"), "I am feeling α");
    }

    @Test
    public void testTextCheckerAndLatextoUnicode() {
        JavaTestKit endProbe = new JavaTestKit(system);
        // LatexToUnicode filter
        Props latexToUnicodeProps = Props.create(LatexToUnicodeActor.class, endProbe.getRef());
        ActorRef latexToUnicodeActor = system.actorOf(latexToUnicodeProps, "latex-to-unicode-actor");
        // TextChecker filter
        Props textCheckerProps = Props.create(TextCheckerActor.class, latexToUnicodeActor);
        ActorRef textCheckerActor = system.actorOf(textCheckerProps, "text-checker-actor");
        // test
        textCheckerActor.tell("I'm wondering whether the answer is \\alpha + \\beta", textCheckerActor);
        endProbe.expectNoMsg(duration("1 second"));
    }

    @Before
    public void setup() {
        system = ActorSystem.create();
    }

    @After
    public void teardown() {
        JavaTestKit.shutdownActorSystem(system);
    }
}
