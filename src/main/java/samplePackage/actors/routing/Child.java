package samplePackage.actors.routing;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

// Define a pool of actors and ask router to route to actors.

public class Child extends AbstractBehavior<Object> {
    public Child(ActorContext<Object> context) {
        super(context);
    }

    public static Behavior<Object> getChild() {
        return Behaviors.setup(Child::new);
    }

    private int invokeCount = 0;

    @Override
    public Receive<Object> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("invoke" , () -> {
                    invokeCount++;
                    getContext().getLog().debug("Child invoked "+invokeCount);
                    System.out.println("This actor got invoked "+getContext().getSelf().path() + " invoked "+invokeCount+" times");
                    return this;
                })
                .onAnyMessage((msg) -> {
                    return Behaviors.stopped();
                })
                .build();
    }
}