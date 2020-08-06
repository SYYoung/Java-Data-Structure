/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] itemList;
    private int total;
    private int startSize = 8;

    // construct an empty randomized queue
    public RandomizedQueue() {
        // allocate 8 blocks
        itemList = (Item[]) new Object[startSize];
        total = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (total == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return total;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (total == itemList.length) resize(2 * itemList.length);
        itemList[total++] = item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < total; i++)
            copy[i] = itemList[i];
        itemList = copy;
    }

    // // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int which2Delete = StdRandom.uniform(0, total);
        Item theItem = itemList[which2Delete];
        if ((total >= 2) && (which2Delete != total - 1))
            itemList[which2Delete] = itemList[total - 1];
        total--;
        return theItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int whichIndex = StdRandom.uniform(0, total);
        return itemList[whichIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        return new QueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> myQueue = new RandomizedQueue<>();
        // 1. test the addFirst/addLast and iterator
        for (int i = 1; i < 10; i++)
            myQueue.enqueue(i);
        StdOut.println("total num of elements: " + myQueue.size());
        StdOut.println("Test iterator: ");
        for (int k : myQueue)
            StdOut.print("\t" + k);
        // test two iterators

        for (int a : myQueue) {
            for (int b : myQueue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }

         
        StdOut.println("\nTest dequeue(): ");
        int beforeRemove = myQueue.size();
        for (int j = 0; j < beforeRemove; j++)
            StdOut.print("\t" + myQueue.dequeue());
        // test sample()
        for (int i = 1; i < 10; i++)
            myQueue.enqueue(i);
        StdOut.println("\nTest sample(): ");
        for (int j = 0; j < myQueue.size() * 3; j++)
            StdOut.print("\t" + myQueue.sample());
    }

    private class QueueIterator implements Iterator<Item> {
        private Item[] copy = (Item[]) new Object[total];
        private int current = 0;

        public QueueIterator() {
            for (int i = 0; i < total; i++)
                copy[i] = itemList[i];
            StdRandom.shuffle(copy);
        }

        public boolean hasNext() {
            return (current != total);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == total)
                throw new java.util.NoSuchElementException();
            Item item = copy[current];
            current++;
            return item;
        }


    }
}
