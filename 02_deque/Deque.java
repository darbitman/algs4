import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {   
    private Node head = null;
    private Node tail = null;
    private int size = 0;
    
    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node current = head;
        
        public boolean hasNext() {
            return current != null;
        }
        
        public Item next() {
            if (current == null)
            {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node newnode = new Node();
        newnode.item = item;
        if (this.size == 0) {
            head = newnode;
            tail = newnode;
        }
        else {
            head.previous = newnode;
            newnode.next = head;
            head = newnode;
        }
        size++;
    }
    
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node newnode = new Node();
        newnode.item = item;
        if (this.size == 0) {
            head = newnode;
            tail = newnode;
        }
        else {
            tail.next = newnode;
            newnode.previous = tail;
            tail = newnode;
        }
        size++;
    }
    
    public Item removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        Item item = head.item;
        if (head == tail) {
            head = null;
            tail = null;
        }
        else {
            head = head.next;
            head.previous = null;
        }
        size--;
        return item;
    }
    
    public Item removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        Item item = tail.item;
        if (head == tail) {
            head = null;
            tail = null;
        }
        else {
            tail = tail.previous;
            tail.next = null;
        }
        size--;
        return item;
    }

    public static void main(String[] args) {
        Deque<Integer> myDeque = new Deque<Integer>();
        myDeque.addLast(1);
        myDeque.addLast(2);
        myDeque.addFirst(3);
        for (Integer x: myDeque) {
            System.out.println(x);
        }
    }
}