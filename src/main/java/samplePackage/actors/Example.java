package samplePackage.actors;

public class Example {

    private static volatile int[] numbers = new int[10];

    public static void main(String args[]){
        final ProducerConsumer pc = new ProducerConsumer();

        Thread consume1 = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Consume-1");
        Thread consume2 = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Consume-2");
        consume1.start();
        consume2.start();

        for(int i=0;i<10;i++){
            int finalI = i;
            Thread t = new Thread(() -> {
                pc.produce(finalI);
            });
            t.start();
        }
    }
    private static class ProducerConsumer {
        private volatile int current = 1;

        public synchronized void produce(int i){
            numbers[i] = i+1;
            notifyAll();
        }

        public void consume() throws InterruptedException {
            synchronized (this) {
                while (current <= 10) {
                    if (current % 2 != 0 && Thread.currentThread().getName().equals("Consume-1")) {
                        while(numbers[current - 1] == 0)
                            wait();
                        notifyAll();
                        System.out.println(Thread.currentThread().getName() + " " + numbers[current - 1]);
                        current++;
                        wait();
                    } else if(current % 2 == 0 && Thread.currentThread().getName().equals("Consume-2")){
                        while(numbers[current - 1] == 0)
                            wait();
                        notifyAll();
                        System.out.println(Thread.currentThread().getName() + " " + numbers[current - 1]);
                        current++;
                        wait();
                    }
                    else
                        wait();
                }
                notifyAll();
            }
        }
    }
}