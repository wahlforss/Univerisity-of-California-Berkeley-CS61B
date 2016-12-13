package synthesizer;

/**
 * Created by Alfred on 09/12/16.
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;

    public int capacity() {
        return capacity;
    }
    public int fillCount() {
        return fillCount;
    }

    public abstract T peek();
    public abstract T dequeue();
    public abstract void enqueue(T x);


}
