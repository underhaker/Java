package queued.stack;

public class Main {

    public static void main(String[] args) {
        QueuedStack stack = new QueuedStack();
        stack.add(3);
        stack.add(5);
        stack.add(7);
        stack.add(9);
        stack.add(1);
        System.out.print("peek:");
        System.out.println(stack.peak());
        System.out.print("pop:");
        System.out.println(stack.pop());
        System.out.print("size:");
        System.out.println(stack.size());

    }
}
