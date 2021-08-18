package samplePackage.actors.stock;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class StockCalculator extends AbstractBehavior<StockCalculatorRequest> {
    public StockCalculator(ActorContext<StockCalculatorRequest> context) {
        super(context);
    }

    public static Behavior<StockCalculatorRequest> createStockCalculator(){
        return Behaviors.setup(StockCalculator::new);
    }

    @Override
    public Receive<StockCalculatorRequest> createReceive() {
        return newReceiveBuilder()
                .onMessage(StockCalculatorRequestStop.class, (cmd) -> Behaviors.stopped())
                .onMessage(StockCalculatorRequest.class, this::calculateStockPrice)
                .build();

    }

    public static double getPrice(String symbol) {
        //Time consuming operation
        try {
            Thread.sleep((int)Math.random() * 10);
        }
        catch(Exception ex) {}
        double price = Math.random() * 1000;
        return price;
    }

    private Behavior<StockCalculatorRequest> calculateStockPrice(StockCalculatorRequest m) {
        BrokerCommand command = new BrokerCommand();
        command.setActor(getContext().getSelf());
        command.setStockPrice(getPrice(m.getSymbol().toString()));
        command.setSymbol(m.getSymbol());
        m.getParentRef().tell(command);
        return this;
    }
}
