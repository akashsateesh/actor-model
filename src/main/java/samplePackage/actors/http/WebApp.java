package samplePackage.actors.http;

import akka.NotUsed;
import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.Scheduler;
import akka.actor.typed.javadsl.AskPattern;
import akka.actor.typed.javadsl.Behaviors;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.server.Route;
import static akka.http.javadsl.server.Directives.*;


import java.time.Duration;
import java.util.concurrent.CompletionStage;

public class WebApp {
    public static void main(String[] args) {
        //define the routes
        //define and start the http server

        //define the actor system

        Behavior<NotUsed> root = Behaviors.setup(context -> {
            WebRoutes routes = new WebRoutes();
            ActorRef<Object> timeActor = context.spawn(Time.getTime(), "timeactor");
            routes.timeActor = timeActor;
            routes.scheduler = context.getSystem().scheduler();
            startHttpServer(routes.getRoutes(), context.getSystem());
            return Behaviors.empty();
        });
        ActorSystem.create(root, "hello-server");
    }


    static void startHttpServer(Route route, final ActorSystem<?> system) {
        CompletionStage<ServerBinding> futureBinding =
                Http.get(system).newServerAt("localhost", 8000).bind(route);

        futureBinding.whenComplete((binding, exception) -> {
            if (binding != null) {
                System.out.println("Server started in 8000");
            } else {
                System.out.println("Stopping server");
                system.terminate();
            }
        });
    }

    public static class WebRoutes {

        private ActorRef<Object> timeActor;
        private Scheduler scheduler;

        public Route getRoutes() {
            return concat(
                    path("hello", () ->
                            get(() -> complete("Hello akka server"))
                    ),
                    path("now", () -> {
                        CompletionStage<String> compStage = AskPattern.ask(timeActor,
                                (ActorRef<String> hiddenRef) -> {
                                    Time.TimeCommand cmd = new Time.TimeCommand();
                                    cmd.setActorRef(hiddenRef);
                                    return cmd;
                                },
                                Duration.ofSeconds(1), scheduler);
                        return get(() -> onSuccess(compStage, performed -> {
                            return complete(performed);
                        }));
                    })
            );
        }
    }
}
