package samplePackage.actors.exceptionHandling;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Parent extends AbstractBehavior<String> {
    public Parent(ActorContext<String> context) {
        super(context);
    }

    public static Behavior<String> getParent(){
        return Behaviors.setup(Parent::new);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("add",() -> {
                    ActorRef<String> child = getContext().spawn(Child.getChild(),"child");
                    child.tell("abd");
                    child.tell("bcd");
                    child.tell("stop");
                    System.out.println("Parent sent");
                    return this;
                })
                .onAnyMessage(msg -> {
                    return Behaviors.stopped();
                })
                .build();
    }
}
