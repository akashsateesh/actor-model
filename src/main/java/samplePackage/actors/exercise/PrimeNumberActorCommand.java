package samplePackage.actors.exercise;

import akka.actor.typed.ActorRef;
import lombok.Data;

@Data
public class PrimeNumberActorCommand {
    private ActorRef<String> ref;
}
