package samplePackage.actors.dispatchers;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class NonBlockingActor extends AbstractBehavior<Object> {
    public NonBlockingActor(ActorContext<Object> context) {
        super(context);
    }

    public static Behavior<Object> getNonBlockingActor(){
        return Behaviors.setup(NonBlockingActor::new);
    }

    @Override
    public Receive<Object> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("print",() -> {
                    getContext().getLog().info("This printer is very fast");
                    return this;
                })
                .onMessageEquals("stop", Behaviors::stopped)
                .build();
    }
}
