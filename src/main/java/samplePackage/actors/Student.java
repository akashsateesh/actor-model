package samplePackage.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import samplePackage.StudentInfo;

import java.util.function.Function;

public class Student extends AbstractBehavior<StudentInfo> {

    public Student(ActorContext<StudentInfo> context) {
        super(context);
    }

    public static Behavior<StudentInfo> createStudentActor(){
        return Behaviors.setup(ctx -> new Student(ctx));
    }

    @Override
    public Receive<StudentInfo> createReceive() {
        return newReceiveBuilder()
                .onMessage(StudentInfo.class, this::test)
                .build();
    }

    private Behavior<StudentInfo> test(StudentInfo studentInfo){
        ActorRef<String> parentActor = studentInfo.getTeacher();
        switch (studentInfo.getMessage()){
            case "start":
                Function<Void,Behavior<StudentInfo>> startFunction = (Void empty) -> writeTest();
                parentActor.tell("finished-test");
                return startFunction.apply(null);
            case "stop":
                Function<Void,Behavior<StudentInfo>> endFucntion = (Void empty) -> stopTest();
                return endFucntion.apply(null);
            default:
                return Behaviors.stopped();
        }
    }

    private Behavior<StudentInfo> stopTest() {
        System.out.println("Student is stoping "+ getContext().getSelf().path());
        return Behaviors.stopped();
    }

    private Behavior<StudentInfo> writeTest() {
        System.out.println("Student is starting "+ getContext().getSelf().path());
        return this;
    }
}
