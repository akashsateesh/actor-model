package samplePackage.actors.routing;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Parent extends AbstractBehavior<Object> {
    public Parent(ActorContext<Object> context) {
        super(context);
    }

    public static Behavior<Object> getParent(){
        return Behaviors.setup(Parent::new);
    }

    @Override
    public Receive<Object> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("invoke",() -> {
                    getContext().getLog().debug("Parent");
                    getContext().spawn(Child.getChild(),"child");
                    return this;
                })
                .onAnyMessage((m) -> Behaviors.stopped())
                .build();
    }
}
