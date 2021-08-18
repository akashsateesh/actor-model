package samplePackage.actors.reactive;

public class Application {
    public static void main(String[] args){
        Publisher<TempInfo> publisher = new Publisher<TempInfo>() {
            @Override
            public void subscribe(Subscriber<? super TempInfo> s) {
                s.onSubscribe(new TempSubscription(s, "California"));
            }
        };
        publisher.subscribe(new TempSubscriber());
    }
}
