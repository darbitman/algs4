import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    
    private int numItems;
    private int arraySize = 2;  // initial array size
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        this.items = (Item[]) new Object[arraySize];
        this.numItems = 0;
    }
    
    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    // return number of items in array
    public int size() {
        return this.numItems;
    }
    
    // add item to queue
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        // double array size if full & copy array contents to new array
        if (numItems == arraySize) {
            this.resize(this.arraySize*2);
        }
        this.items[numItems++] = item;
    }
    
    // remove and return a random item
    public Item dequeue() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException("Tried dequeue-ing empty queue");
        }
        int randIndex = StdRandom.uniform(this.size());
        Item randItem = this.items[randIndex];
        this.numItems--;
        if (randIndex < this.size()) {
            this.items[randIndex] = this.items[this.size()];
        }
        this.items[this.size()] = null;
        if ((this.size() > 0) && (this.size() <= this.arraySize/4)) {
            resize(this.arraySize/2);
        }
        return randItem;
    }
    
    public Item sample() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException("Tried sampling empty queue");
        }
        return this.items[StdRandom.uniform(this.size())];
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] iteratorItems;
        private final int numIteratorItems;
        private int position = 0;
        
        private RandomizedQueueIterator() {
            this.iteratorItems = (Item[]) new Object[size()];
            for (int i = 0; i < size(); i++) {
                this.iteratorItems[i] = items[i];
            }
            StdRandom.shuffle(iteratorItems);
            this.numIteratorItems = size();
        }
        
        public boolean hasNext() {
            if (this.position < this.numIteratorItems) {
                return true;
            }
            return false;
        }
        
        public Item next() {
            if (this.position == this.numIteratorItems) {
                throw new java.util.NoSuchElementException();
            }
            return iteratorItems[position++];
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException("Tried to use remove()");
        }
    }
    
    private void resize(int newCapacity) {
        this.arraySize = newCapacity;
        Item[] newarray = (Item[]) new Object[newCapacity];
        for (int i = 0; i < this.numItems; i++) {
            newarray[i] = this.items[i];
        }
        this.items = newarray;
    }
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> x = new RandomizedQueue<Integer>();
        x.enqueue(1);
        x.enqueue(2);
        x.enqueue(3);
        x.enqueue(4);
        x.enqueue(5);
        x.enqueue(6);
        x.enqueue(7);
        x.enqueue(8);
        for (int y1: x) {
            System.out.println(y1);
        }
        System.out.println("dequeue random: " + x.dequeue());
        System.out.println("dequeue random: " + x.dequeue());
        System.out.println("dequeue random: " + x.dequeue());
        System.out.println("dequeue random: " + x.dequeue());
        for (int y2: x) {
            System.out.println(y2);
        }
    }
}