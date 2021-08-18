package samplePackage;

import akka.actor.typed.ActorRef;
import lombok.Data;

@Data
public class StudentInfo {
    private String message;
    private ActorRef<String> teacher;
}
