package samplePackage.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Temperature extends AbstractBehavior<String> {

    Map<String,Integer> map;

    public Temperature(ActorContext<String> context) {
        super(context);
        map = new HashMap<>();
    }

    public static Behavior<String> create() {
        return Behaviors.setup(Temperature::new);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("Chennai", () -> {
                    int temp = new Random().nextInt();
                    System.out.println("Chennai temperature "+ temp);
                    map.put("Chennai",temp);
                    return this;
                })
                .onMessageEquals("Bangalore", () -> {
                    int temp = new Random().nextInt();
                    System.out.println("Bangalore temperature "+temp);
                    map.put("Bangalore",temp);
                    return this;
                })
                .onMessageEquals("history", () -> {
                    System.out.println("Printing history.....");
                    for(String city:map.keySet()){
                        System.out.println(city + " "+ map.get(city));
                    }
                    return this;
                })
                .onMessageEquals("quit", Behaviors::stopped)
                .onAnyMessage(msg -> {
                    System.out.println("I don't know "+msg);
                    return this;
                })
                .build();
    }
}
