package samplePackage.actors.exercise;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.Data;
import samplePackage.actors.askPattern.Command;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MathActor extends AbstractBehavior<Object> {

    private final int num;
    private ActorRef<Object> primeNumberActor;
    private ActorContext<Object> actorContext;
    List<String> primeNumbers;

    public MathActor(ActorContext<Object> context) {
        super(context);
        num = 20;
        primeNumberActor = getContext().spawn(PrimeNumberActor.getPrimeNumberActor(),"primeNumberActor");
        actorContext = getContext();
        primeNumbers = new ArrayList<>();
    }

    public static Behavior<Object> getMathActor(){
        return Behaviors.setup(MathActor::new);
    }

    @Override
    public Receive<Object> createReceive() {
        return newReceiveBuilder()
                .onMessage(Command.class, (cmd) -> {
                    for(int i=1;i<=num;i++){
                        final int primeIndex = i;
                         actorContext.ask(
                                String.class,
                                primeNumberActor,
                                Duration.ofSeconds(100),
                                tempActor -> {
                                    Command command = new Command();
                                    command.setRef(tempActor);
                                    return command;
                                },
                                (r, t) -> {
                                    if(r != null){
                                        System.out.println("Prime Number "+primeIndex+" "+r);
                                        primeNumbers.add(r);
                                    }
                                    else {
                                        System.out.println("PrimeNumber Actor didn't respond for "+primeIndex+"th prime number");
                                    }
                                    if(primeNumbers.size() == num){
                                        cmd.getRef().tell("Computed All Primes");
                                    }
                                    return r;
                                }
                         );
                    }
                    return this;
                })
                .onAnyMessage(msg -> {
                    System.out.println(" From Math Actor "+msg);
                    return this;
                })
                .build();
    }

    @Data
    public static class MathActorCommand {
        private ActorRef<String> actorRef;
        private String msg;
    }
}
