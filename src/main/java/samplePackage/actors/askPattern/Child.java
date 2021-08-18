package samplePackage.actors.askPattern;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.time.LocalDateTime;

public class Child extends AbstractBehavior<Object> {
    public Child(ActorContext<Object> context) {
        super(context);
    }

    public static Behavior<Object> getChild() {
        return Behaviors.setup(Child::new);
    }

    @Override
    public Receive<Object> createReceive() {
        return newReceiveBuilder()
                .onMessage(Command.class, cmd -> {
                    cmd.getRef().tell("It's "+ LocalDateTime.now()+" from Child");
                    return this;
                })
                .build();
    }
}
