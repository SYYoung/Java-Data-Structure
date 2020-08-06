/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;

public class RandomizedQOld<Item> implements Iterable<Item> {

    private Node<Item> first, last;
    private int total;

    // construct an empty randomized queue
    public RandomizedQOld() {
        first = null;
        last = null;
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
    }

    // // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        return null;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        return null;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        return new NodeIterator();
    }

    public static void main(String[] args) {

    }

    private class Node<Item> {
        private Item element;
        private Node<Item> prev;
        private Node<Item> next;

        public Node(Item ele) {
            element = ele;
            prev = null;
            next = null;
        }

    }

    private class NodeIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null)
                throw new java.util.NoSuchElementException();
            Item item = current.element;
            current = current.next;
            return item;
        }
    }
}
