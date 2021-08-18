package samplePackage.actors.exceptionHandling;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Child extends AbstractBehavior<String> {
    public Child(ActorContext<String> context) {
        super(context);
    }

    public static Behavior<String> getChild() {
        return Behaviors.setup(Child::new);
    }

    private String state = "INTITAL";

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("abd", () -> {
                    state += "-ADD";
                    System.out.println("Child add - state is "+ state);
                    return this;
                })
                .onMessageEquals("bcd", () -> {
                    state += "-DIVIDE";
                    System.out.println("Child divide Going to die - state is "+ state);
                    int a = 2/0;
                    System.out.println("Child divide - state is "+ state);
                    return Behaviors.same();
                })
                .onAnyMessage((msg) -> {
                    return Behaviors.stopped();
                })
                .build();
    }
}
