package queued.stack;

public interface StackInterface {
    // Adds an element to the stack.
    public void add(int element);

    // returns the next element that should be poped without popping it.
    public int peak();

    // Returns and removes the last element of the stack.
    public int pop();

    // Returns the current size of the stack
    public int size();

    // Checks if the stack is empty.
    public boolean isEmpty();
}