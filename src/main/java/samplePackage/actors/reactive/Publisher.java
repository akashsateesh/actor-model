package samplePackage.actors.reactive;

@FunctionalInterface
public interface Publisher<T> {
    void subscribe(Subscriber<? super T> s);
}

