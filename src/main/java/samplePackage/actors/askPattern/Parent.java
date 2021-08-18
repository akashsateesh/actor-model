package samplePackage.actors.askPattern;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;

public class Parent extends AbstractBehavior<Object> {
    public Parent(ActorContext<Object> context) {
        super(context);
    }

    public static Behavior<Object> getParent() {
        return Behaviors.setup(Parent::new);
    }

    @Override
    public Receive<Object> createReceive() {
        return newReceiveBuilder()
                .onMessage(Command.class, cmd -> {
                    ActorRef<Object> child = getContext().spawn(Child.getChild(),"child");
                    getContext().ask(String.class,child, Duration.ofSeconds(2),
                            tempActor -> {
                                Command command = new Command();
                                command.setRef(tempActor);
                                return command;
                            }, (r,t) -> {
                                if(r != null)
                                    cmd.getRef().tell("Got a message from child "+ LocalDateTime.now());
                                else
                                    System.out.println("Child has not sent a response yet");
                                return r;
                            });
                    return this;
                })
                .onAnyMessage(msg -> {
                    System.out.println(" from Another msg ");
                    return this;
                })
                .build();
    }
}
