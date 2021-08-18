package samplePackage.actors.dispatchers;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class BlockingActor extends AbstractBehavior<Object> {
    public BlockingActor(ActorContext<Object> context) {
        super(context);
    }

    public static Behavior<Object> getBlockingActor(){
        return Behaviors.setup(BlockingActor::new);
    }

    @Override
    public Receive<Object> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("print",() -> {
                    Thread.sleep(15000);
                    getContext().getLog().info("This Printer is very slow");
                    return Behaviors.same();
                })
                .onMessageEquals("stop",() -> Behaviors.stopped())
                .build();
    }
}
