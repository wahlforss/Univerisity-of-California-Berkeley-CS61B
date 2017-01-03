import sun.awt.image.ImageWatched;

/**
 * Created by Alfred on 29/11/16.
 */
public class LinkedListDeque<Item> {
    public Item first;
    public LinkedListDeque<Item> next;

    public LinkedListDeque(Item first0, LinkedListDeque<Item> next0) {
        first = first0;
        next = next0;

    }
    private LinkedListDeque sentinel;
    private LinkedListDeque prev;
    private int size;

    public int size() {
        return size;
    }
    //creates linkedlist size 0
    public LinkedListDeque() {
        size = 0;
        sentinel = new LinkedListDeque(null,null);
        prev = sentinel;
        sentinel.next = sentinel;

    }
    //return whether list is empty or not
    public boolean isEmpty() {
        if(size==0) {
            return true;
        }
        else {
            return false;
        }
    }
    public Item getFirst() {
        return (Item) sentinel.next.first;
    }
    //creates linkedlist size 1
    public LinkedListDeque(Item x) {
        size = 1;
        sentinel = new LinkedListDeque(null,null);
        sentinel.next = new LinkedListDeque(x,null);
        prev = sentinel.next;
    }
    public void addFirst(Item item) {
        if(size == 0) {
            LinkedListDeque<Item> t = new LinkedListDeque(item);
            sentinel = t.sentinel;
            sentinel.next = t.sentinel.next;
            prev = t.prev;
            size = t.size;

        }
        else {
            size += 1;
            LinkedListDeque<Item> oldFront = sentinel.next;
            sentinel.next = new LinkedListDeque(item,oldFront);
            oldFront.prev = sentinel.next;
        }

    }

    public Item getLast() {
        return (Item) prev.first;
    }

    public void addLast(Item item) {
        if(size == 0) {
            LinkedListDeque<Item> t = new LinkedListDeque(item);
            sentinel = t.sentinel;
            sentinel.next = t.sentinel.next;
            prev = t.prev;
            size = t.size;

        }
        else {
            size += 1;
            LinkedListDeque<Item> oldBack = prev;
            prev = new LinkedListDeque(item,sentinel);
            prev.prev = oldBack;
            oldBack.next = prev;
        }
    }
    public Item get(int index) {
        int place = 0;
        LinkedListDeque<Item> p = sentinel.next;
        while(place != index) {
            place += 1;
            p = p.next;
        }
        return p.first;
    }
    //Use for getRec.
    private Item getRecHelper(int index) {
        if(index == 0) return first;
        return next.getRecHelper(index-1);

    }
    public Item getRec(int index) {
        if(index == 0) return getFirst();
        return (Item) sentinel.next.getRecHelper(index);
    }

    public void printDeque() {
        int place = 0;
        while(place != size) {
            System.out.print(this.get(place) + " ");
            place += 1;
        }
    }

    public Item removeFirst() {
        if(size == 0) return null;
        Item frontItem = this.getFirst();
        sentinel.next = sentinel.next.next;
        size -= 1;
        return frontItem;
    }
    public Item removeLast() {
        if(size == 0) return null;
        Item lastItem = this.getLast();
        prev = prev.prev;
        size -= 1;
        return lastItem;
    }



    public static void main(String[] args) {
        LinkedListDeque<String> t = new LinkedListDeque();
        t.addFirst("front");
        t.addFirst("Frontf");
        t.addLast("middle");
        t.addLast("lastls");
        LinkedListDeque<String> s = new LinkedListDeque();
        System.out.println(s.getRec(0));
        t.printDeque();
    }
}
