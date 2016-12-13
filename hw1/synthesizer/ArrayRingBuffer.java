package synthesizer;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;
    public ArrayRingBuffer<T> lol = this;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        first = 0;
        last = 0;
        this.capacity = capacity;
        rb = (T[])(new Object[capacity]);
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if(isFull()) throw new RuntimeException("Ring Buffer Overflow");
        rb[last] = x;
        fillCount += 1;
        last += 1;
        if(last == capacity) last = 0;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update first
        if(isEmpty()) throw new RuntimeException("Ring Buffer Underflow");
        T firstItem = peek();
        first += 1;
        fillCount -= 1;
        if(first == capacity) first = 0;
        return firstItem;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
    public class ArrayRingIterator implements Iterator<T> {
        private int index;
        private int beforeFirst;
        private int beforeFillCount;

        public ArrayRingIterator() {
            index = 0;
            beforeFirst = first;
            beforeFillCount = fillCount();
        }
        public boolean hasNext() {
            if(isEmpty()) {
                fillCount = beforeFillCount;
                first = beforeFirst;
                return false;
            } else {
                return true;
            }
        }
        public T next() {
            T currentThing = dequeue();
            return currentThing;
        }

    }
    public Iterator<T> iterator() {
        return new ArrayRingIterator();
    }

}
