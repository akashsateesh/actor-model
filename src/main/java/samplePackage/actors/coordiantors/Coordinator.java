package samplePackage.actors.coordiantors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import lombok.Data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Coordinator extends AbstractBehavior<Object> {
    public Coordinator(ActorContext<Object> context) {
        super(context);
        input = "409  194  207  470  178  454  235  333  511  103  474  293  525  372  408  428 4321  2786  6683  3921  265  262  6206  2207  5712  214  6750  2742  777  5297 3536  2675  1298  1069  175  145  706  2614  4067  4377  146  134  1930  3850 2169  1050  3705  2424  614  3253  222  3287  3340  2637  61  216  2894  247 214 99  797  80  683  789  92  736  318  103  153  749  631  626  367  110  805 2922  1764  178  3420  3246  3456  73  2668  3518  1524  273  2237  228  1826  182 4682  642  397  5208  136  4766  180  1673  1263  4757  4680  141  4430  1098  188 158  712  1382  170  550  913  191  163  459  1197  1488  1337  900  1182  1018  337 3858  202  1141  3458  2507  239  199  4400  3713  3980  4170  227  3968  1688  4352 4168 209";
        nums = input.split("\\s+");
        sumOfDigits = new ArrayList<>();
        bigInteger = new BigInteger("1");
    }

    private BigInteger bigInteger;
    private String input;
    private String[] nums;
    private List<Long> sumOfDigits;

    public static Behavior<Object> getCoordinator() {
        return Behaviors.setup(Coordinator::new);
    }

    @Override
    public Receive<Object> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("checksum",this::computeCheckSum)
                .onMessage(CoordinatorRequestFinish.class,this::printResult)
                .onMessage(CoordinatorRequestStop.class,this::stopCoordinator)
                .onMessage(CoordinatorRequest.class,this::fetchCheckSumResult)
                .build();
    }

    private Behavior<Object> stopCoordinator(CoordinatorRequestStop m) {
        if(sumOfDigits.size() == nums.length)
            return Behaviors.stopped();
        else
            getContext().getSelf().tell(new CoordinatorRequestStop());
        return this;
    }

    private <M> Behavior<Object> printResult(M m) {
        if(sumOfDigits.size() == nums.length) {
            System.out.println("CheckSum Result " + bigInteger.toString());
            getContext().getSelf().tell(new Coordinator.CoordinatorRequestStop());
        }
        else
            getContext().getSelf().tell(new CoordinatorRequestFinish());
        return this;
    }

    private  Behavior<Object> fetchCheckSumResult(CoordinatorRequest coordinatorRequest) {
        long checkSum = coordinatorRequest.getCheckSum();
        sumOfDigits.add(checkSum);
        if(sumOfDigits.size() == nums.length){
            for(long sum:sumOfDigits) {
                bigInteger = bigInteger.multiply(new BigInteger(String.valueOf(sum)));
                System.out.println("Checksum val so far " +bigInteger.toString());
            }
        }
        return this;
    }

    private Behavior<Object> computeCheckSum() {
        PoolRouter<Object> poolRouter = Routers.pool(5,SumActor.getSumActor()).withRoundRobinRouting();
        ActorRef<Object> actorRef = getContext().spawn(poolRouter,"pool-router");
        for(String num:nums){
            SumActor.SumActorRequest sumActorRequest = new SumActor.SumActorRequest();
            sumActorRequest.setParent(getContext().getSelf());
            sumActorRequest.setNum(num);
            actorRef.tell(sumActorRequest);
        }
        return this;
    }

    @Data
    public static class CoordinatorRequest {
        private long checkSum;
    }

    public static class CoordinatorRequestFinish {

    }

    public static class CoordinatorRequestStop{}
}
