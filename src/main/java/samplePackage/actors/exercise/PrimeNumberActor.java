package samplePackage.actors.exercise;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import samplePackage.actors.askPattern.Command;

import java.math.BigInteger;
import java.util.Random;

public class PrimeNumberActor extends AbstractBehavior<Object> {
    public PrimeNumberActor(ActorContext<Object> context) {
        super(context);
    }

    public static Behavior<Object> getPrimeNumberActor(){
        return Behaviors.setup(PrimeNumberActor::new);
    }

    @Override
    public Receive<Object> createReceive() {
        return newReceiveBuilder()
                .onMessage(Command.class, cmd -> {
                    BigInteger bigInteger = new BigInteger(3000, new Random());
                    cmd.getRef().tell(bigInteger.nextProbablePrime().toString());
                    return this;
                })
                .build();
    }
}
