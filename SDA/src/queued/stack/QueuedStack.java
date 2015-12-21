package queued.stack;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueuedStack implements StackInterface {
    private Queue<Integer> container;

    public QueuedStack() {
        container = new ConcurrentLinkedQueue<Integer>();
    }

    @Override
    public void add(int element) {
        container.offer(element);
    }

    @Override
    public int peak() {
        return container.peek();
    }

    @Override
    public int pop() {
        return container.poll();
    }

    @Override
    public int size() {
        return container.size();
    }

    @Override
    public boolean isEmpty() {
        return container.isEmpty();
    }

}
