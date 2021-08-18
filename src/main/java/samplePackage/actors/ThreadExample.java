package samplePackage.actors;

import java.util.concurrent.Semaphore;

public class ThreadExample {
    public static void main(String[] args){
        Math math = new Math();
        Thread t1 = new Thread(() -> {
            try {
                math.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Consume-1");

        Thread t2 = new Thread(() -> {
            try {
                math.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Consume-2");

        t1.start();
        t2.start();

    }
        
    public static  class Math {
        public volatile int len = 100;
        public volatile int start = 0;
        
        public synchronized void consume() throws InterruptedException {
            while(start <= len){
                if(start % 2 == 0 && java.lang.Thread.currentThread().getName().equals("Consume-1")){
                    System.out.println(start + " "+ java.lang.Thread.currentThread().getName());
                    start++;
                    notifyAll();
                }
                else if(java.lang.Thread.currentThread().getName().equals("Consume-1")){
                    while(start % 2 == 1)
                        wait();
                }
                else if(start % 2 == 1 && java.lang.Thread.currentThread().getName().equals("Consume-2")){
                    System.out.println(start + " "+ java.lang.Thread.currentThread().getName());
                    start++;
                    notifyAll();
                }
                else{
                    while(start % 2 == 0)
                        wait();
                }
            }
        }
    }
}
