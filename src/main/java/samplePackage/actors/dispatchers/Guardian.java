package samplePackage.actors.dispatchers;

import akka.actor.typed.Behavior;
import akka.actor.typed.DispatcherSelector;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Guardian extends AbstractBehavior<Object> {
    public Guardian(ActorContext<Object> context) {
        super(context);
    }

    public static Behavior<Object> getGuardian(){
        return Behaviors.setup(Guardian::new);
    }

    @Override
    public Receive<Object> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("show", () -> {
                    for(int i=0;i<30;i++){
                        getContext().spawn(NonBlockingActor.getNonBlockingActor(),"fast-"+i).tell("print");
                        getContext().spawn(BlockingActor.getBlockingActor(),"slow-"+i, DispatcherSelector.fromConfig("slow-printer-dispatcher")).tell("print");
                    }
                    return this;
                })
                .build();
    }
}
