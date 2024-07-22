package blockingqueue;

import java.util.LinkedList;

public class BlockingQueue<T> {
    private static final int compacity = 10;
    private final LinkedList<T> items = new LinkedList<>();

    public synchronized void put(T value) throws InterruptedException {
        while (items.size() == compacity) {
            System.out.println("Queue is full");
            wait();
        }
        items.addLast(value);
        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (items.isEmpty()) {
            System.out.println("Queue is empty");
            wait();
        }
        notifyAll();
        return items.removeFirst();
    }

    public synchronized int size() {
        return items.size();
    }

    public synchronized int remainingCapacity() {
        return compacity - items.size();
    }
}
