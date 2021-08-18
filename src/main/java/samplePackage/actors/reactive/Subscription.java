package samplePackage.actors.reactive;

public interface Subscription {
    void request(long n);
    void cancel();
}
