package samplePackage.actors.stock;

import akka.actor.typed.ActorRef;
import lombok.Data;

@Data
public class BrokerCommand {
    private Double stockPrice;
    private Symbol symbol;
    private ActorRef<StockCalculatorRequest> actor;
}

