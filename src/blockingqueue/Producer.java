package blockingqueue;

import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;

    Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                queue.put(produce());
                System.out.println("Produced resource = "  + queue.remainingCapacity());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int produce() throws InterruptedException {
        Thread.sleep(50);
        return ThreadLocalRandom.current().nextInt(1, 100);
    }
}
