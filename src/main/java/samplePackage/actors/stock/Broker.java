package samplePackage.actors.stock;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Broker extends AbstractBehavior<BrokerCommand> {

    List<ActorRef<StockCalculatorRequest>> list;
    List<Symbol> symbols;
    Map<Symbol,Double> stockPrices;

    public Broker(ActorContext<BrokerCommand> context) {
        super(context);
        list = new ArrayList<>();
        symbols = new ArrayList<>();
        stockPrices = new HashMap<>(5);
        symbols.add(Symbol.IBM);
        symbols.add(Symbol.GOOGLE);
        symbols.add(Symbol.WALMART);
        symbols.add(Symbol.INTUIT);
        symbols.add(Symbol.FACEBOOK);
    }

    public static  Behavior<BrokerCommand> getBroker(){
        return Behaviors.setup(Broker::new);
    }

    @Override
    public Receive<BrokerCommand> createReceive() {
        return newReceiveBuilder()
                .onMessage(BrokerCommandStart.class, this::calculate)
                .onMessage(BrokerCommandStop.class,this::stop)
                .onMessage(BrokerCommandCheckFinished.class,this::checkIfCompleted)
                .onMessage(BrokerCommand.class, this::addToStockPriceList)
                .build();
    }
    
    private <M> Behavior<BrokerCommand> checkIfCompleted(M m) {
        if(symbols.size() == stockPrices.size())
            System.out.println(" It's Completed.");
        else
            System.out.println("Still in Progress");
        return this;
    }

    private Behavior<BrokerCommand> addToStockPriceList(BrokerCommand command) {
            stockPrices.put(command.getSymbol(),command.getStockPrice());
            System.out.println("Stock price Received from Actor "+ command.getActor().path());
            command.getActor().tell(new StockCalculatorRequestStop());
            if(stockPrices.size() == symbols.size()){
                stockPrices.forEach((k,v) -> {
                    System.out.println(String.format("Stock %s price is %s",k,v));
                });
            }
            return this;
    }
    
    private Behavior<BrokerCommand> calculate(BrokerCommandStart commandStart) {
        for(int i=1;i<=5;i++){
            list.add(getContext().spawn(StockCalculator.createStockCalculator(),"stock-calculator-"+i));
        }
        for(int i=1;i<=5;i++){
            StockCalculatorRequest stockCalculatorRequest = new StockCalculatorRequest();
            stockCalculatorRequest.setParentRef(getContext().getSelf());
            stockCalculatorRequest.setSymbol(symbols.get(i-1));
            list.get(i-1).tell(stockCalculatorRequest);
        }
        System.out.println("Called Broker command");
        return this;
    }

    private <M> Behavior<BrokerCommand> stop(M m) {
        if(stockPrices.size() < symbols.size()){
            getContext().getSelf().tell(new BrokerCommandStop());
            return this;
        }
        return Behaviors.stopped();
    }
}
