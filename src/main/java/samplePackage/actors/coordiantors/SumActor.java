package samplePackage.actors.coordiantors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.Data;

public class SumActor extends AbstractBehavior<Object> {
    public SumActor(ActorContext<Object> context) {
        super(context);
    }

    public static Behavior<Object> getSumActor(){
        return Behaviors.setup(SumActor::new);
    }

    @Override
    public Receive<Object> createReceive() {
        return newReceiveBuilder()
                .onMessage(SumActorRequest.class,this::computeCheckSum)
                .onAnyMessage((msg) -> Behaviors.stopped())
                .build();
    }

    private Behavior<Object> computeCheckSum(SumActorRequest sumActorRequest) {
        String num = sumActorRequest.getNum();
        Coordinator.CoordinatorRequest coordinatorRequest = new Coordinator.CoordinatorRequest();
        long ans = 0;
        int len = num.length();
        for(int i=0;i<len;i++){
            ans += (num.charAt(i) - '0');
        }
        coordinatorRequest.setCheckSum(ans);
        sumActorRequest.getParent().tell(coordinatorRequest);
        return this;
    }

    @Data
    public static class SumActorRequest {
        private ActorRef<Object> parent;
        private String num;
    }
}
