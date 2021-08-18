package samplePackage.actors.stock;

import akka.actor.typed.ActorRef;
import lombok.Data;

@Data
public class StockCalculatorRequest {
    private ActorRef<BrokerCommand> parentRef;
    private Symbol symbol;
}

