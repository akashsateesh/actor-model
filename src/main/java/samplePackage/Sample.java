package samplePackage;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;

import akka.actor.typed.javadsl.AskPattern;
import akka.actor.typed.javadsl.PoolRouter;
import akka.actor.typed.javadsl.Routers;
import org.springframework.core.task.SyncTaskExecutor;
import samplePackage.actors.askPattern.Command;
import samplePackage.actors.askPattern.Parent;
import samplePackage.actors.coordiantors.Coordinator;
import samplePackage.actors.dispatchers.Guardian;
import samplePackage.actors.exercise.MathActor;
import samplePackage.actors.routing.Child;
import samplePackage.actors.stock.*;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Sample {
    public static void main(String[] args) {
//        ActorSystem<BrokerCommand> brokerCommandActorSystem = ActorSystem.create(Broker.getBroker(),"broker-actor");
//        brokerCommandActorSystem.tell(new BrokerCommandStart());
//        brokerCommandActorSystem.tell(new BrokerCommandCheckFinished());
//        brokerCommandActorSystem.tell(new BrokerCommandStop());
//        PoolRouter<Object> router = Routers.pool(3,Child.getChild()).withConsistentHashingRouting(50,msg -> msg.toString());
//        ActorSystem<Object> system = ActorSystem.create(router,"router-system");
//        ActorSystem<Object> parent = ActorSystem.create(Child.getChild(),"child");
//        for (int i=0;i<10;i++){
//            system.tell("invoke");
//        }
//        system.tell("stop");
//        ActorSystem<Object> actorSystem = ActorSystem.create(Coordinator.getCoordinator(),"coordinator");
//        actorSystem.tell("checksum");
//        actorSystem.tell(new Coordinator.CoordinatorRequestFinish());
//        ActorSystem<Object> actorSystem = ActorSystem.create(Guardian.getGuardian(),"guardian");
//        actorSystem.tell("show");
//        ActorSystem<Object> askActor = ActorSystem.create(Parent.getParent(),"ask-parent");
//        CompletionStage<String> completableFuture = AskPattern.ask(askActor,
//                (ActorRef<String> ref) -> {
//                    Command command = new Command();
//                    command.setRef(ref);
//                    return command;
//                }, Duration.ofSeconds(2), askActor.scheduler());
//        completableFuture.whenComplete((response, throwable) -> {
//            if(response != null)
//                System.out.println(response);
//            else
//                System.out.println("No Response");
//        });
//
//
//        ActorSystem<Object> askActor = ActorSystem.create(MathActor.getMathActor(),"ask-mathActor");
//        CompletionStage<String> completionStage = AskPattern.ask(askActor,
//                (ActorRef<String> tempActor) -> {
//                    Command command = new Command();
//                    command.setRef(tempActor);
//                    return command;
//                },
//                Duration.ofSeconds(50),
//                askActor.scheduler()
//                );
//        completionStage.whenComplete((r,t) -> {
//            if(r != null){
//                System.out.println(r);
//            }
//            else   {
//                System.out.println("Math Actor did not responsd");
//            }
//        });
    
        CompletableFuture<String> completableFuture =
                CompletableFuture.supplyAsync(
                        () -> {
                            return "Hello";
                        },
                        new SyncTaskExecutor()
                );
                completableFuture.thenAccept(
                        (t) -> {
                            System.out.println(t);
                        }  
                );
    }
}
