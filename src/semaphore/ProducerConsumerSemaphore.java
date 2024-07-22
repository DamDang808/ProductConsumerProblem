package semaphore;

import java.util.Stack;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class ProducerConsumerSemaphore {

    private final int capacity = 10;
    private final Semaphore empty = new Semaphore(capacity);
    private final Semaphore full = new Semaphore(0);
    private Stack<Integer> buffer = new Stack<>();

    class Producer implements Runnable {
        private String name;

        public Producer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int item =(int) (Math.random() * 100);
                    empty.acquire();
                    buffer.push(item);
                    System.out.println(name + ": produced data " + buffer.peek());
                    full.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Consumer implements Runnable {
        private String name;

        public Consumer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    full.acquire();
                    System.out.println(name + ": consumed data " + buffer.pop());
                    empty.release();
                    Thread.sleep(ThreadLocalRandom.current().nextInt(50, 300)); // simulate time passing
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ProducerConsumerSemaphore obj = new ProducerConsumerSemaphore();
        Producer producer = obj.new Producer("Producer 1");
        new Thread(producer).start();

        for (int i = 1; i <= 3; i++) {
            Consumer consumer = obj.new Consumer("Consumer " + i);
            new Thread(consumer).start();
        }

        Thread.sleep(5000); // After 5s have another comsumer
        Consumer consumer = obj.new Consumer("Consumer 4");
        new Thread(consumer).start();
    }
}