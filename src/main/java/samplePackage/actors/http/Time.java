package samplePackage.actors.http;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.Data;

import java.time.LocalDateTime;

public class Time extends AbstractBehavior<Object> {
    public Time(ActorContext<Object> context) {
        super(context);
    }

    public static Behavior<Object> getTime(){
        return Behaviors.setup(Time::new);
    }

    @Override
    public Receive<Object> createReceive() {
        return newReceiveBuilder()
                .onMessage(TimeCommand.class, (msg) -> {
                    msg.getActorRef().tell("It's "+ LocalDateTime.now());
                    return this;
                })
                .onAnyMessage(msg -> {
                    System.out.println(msg);
                    return this;
                })
                .build();
    }

    @Data
    public static class TimeCommand {
        ActorRef<String> actorRef;
    }
}
