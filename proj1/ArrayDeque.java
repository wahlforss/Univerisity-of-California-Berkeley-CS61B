/**
 * Created by Alfred on 05/12/16.
 */
public class ArrayDeque<Item> {
    private Item[] items;
    private int size;

    private static int RFACTOR = 3;

    public ArrayDeque() {
        size = 0;
        items = (Item[]) new Object[8];
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        if(size == 0) return true;
        return false;
    }
    private void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];
        System.arraycopy(items,0,a,0,size);
        items = a;
    }
    public void addLast(Item x) {
        if(size == items.length) {
            resize(size*RFACTOR);
        }
        items[size] = x;
        size += 1;
    }
    public Item getFirst() {
        return items[0];
    }
    public Item getLast() {
        return items[size-1];
    }

    public Item removeLast() {
        Item lastItem = getLast();
        items[size-1] = null;
        size = size - 1;
        if(16 < size && items.length/RFACTOR > size*RFACTOR) {
            resize(items.length/RFACTOR);
        }
        return lastItem;
    }

    public Item removeFirst() {
        Item firstItem = getFirst();
        Item[] a = (Item[]) new Object[items.length];
        System.arraycopy(items,1,a,0,size-1);
        items = a;
        size -= 1;
        if(16 < size && items.length*0.25 > size) {
            resize(items.length/RFACTOR);
        }
        return firstItem;

    }
    public void addFirst(Item x) {
        Item[] a = (Item[]) new Object[items.length];
        System.arraycopy(items,0,a,1,size);
        a[0] = x;
        size += 1;
        items = a;
    }

    public Item get(int index) {
        if(index > size - 1) return (Item) "no index";
        return items[index];
    }
    public void printDeque() {
        int i = 0;
        while(i != size) {
            System.out.print(get(i)+ " ");
            i += 1;
        }
    }
    public int length() {
        return items.length;
    }


    public static void main(String[] args) {
        ArrayDeque<Integer> t = new ArrayDeque();
        int  i = 0;
        while(i < 100000) {
            t.addLast(i);
            i += 1;
        }
        i = 0;
        while(i < 80000) {
            t.removeFirst();
            i += 1;
        }

        System.out.println(t.size());
        System.out.println(t.length());
        System.out.println(t.get(1999));


    }
}
