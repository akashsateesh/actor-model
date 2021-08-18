package samplePackage.loadBalancer;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class LoadBalancer {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        TokenBucket tokenBucket  = new TokenBucket();

        ExecutorService executorService = Executors.newFixedThreadPool(14);

        List<Callable<Boolean>> callables = new ArrayList<>();

        for(int i=1;i<=100;i++){

            callables.add(() -> tokenBucket.isActive("persona","123",1000L,10));

        }

        List<Future<Boolean>> futures = executorService.invokeAll(callables);

        for (Future future:futures){
            System.out.println(future.get());
        }

    }

}
