package samplePackage.actors.askPattern;

import akka.actor.typed.ActorRef;
import lombok.Data;

@Data
public class Command {
    private ActorRef<String> ref;
}
