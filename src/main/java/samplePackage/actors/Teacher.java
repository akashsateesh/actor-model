package samplePackage.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import samplePackage.StudentInfo;

public class Teacher extends AbstractBehavior<String> {
    public Teacher(ActorContext<String> context) {
        super(context);
        studentActor = getContext().spawn(Student.createStudentActor(),"student-1");
    }

    public static Behavior<String> getTeacher(){
        return Behaviors.setup(Teacher::new);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("start" , () -> startTest())
                .onMessageEquals("stop", () -> stopTest())
                .onMessageEquals("finished-test", () -> {
                    System.out.println("Great!, This student has finished the test");
                    return this;
                })
                .build();
    }

    private ActorRef<StudentInfo> studentActor = null;

    private Behavior<String> stopTest() {
        System.out.println("Teacher stopping "+getContext().getSelf().path());
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setMessage("stop");
        studentInfo.setTeacher(getContext().getSelf());
        studentActor.tell(studentInfo);
        return Behaviors.stopped();
    }

    private Behavior<String> startTest() {
        System.out.println("Teacher starting "+getContext().getSelf().path());
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setMessage("start");
        studentInfo.setTeacher(getContext().getSelf());
        studentActor.tell(studentInfo);
        return this;
    }
}
