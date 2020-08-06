import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first, last;
    private int total;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        total = 0;
    }

    // is the deque empty
    public boolean isEmpty() {
        return (total == 0);
    }

    // return the number of items on the deque
    public int size() {
        return total;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node<Item> oldFirst = first;
        first = new Node<Item>(item);
        first.next = oldFirst;
        if (isEmpty()) last = first;
        else oldFirst.prev = first;
        total++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node<Item> oldLast = last;
        last = new Node<Item>(item);
        last.prev = oldLast;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        total++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Node<Item> oldFirst = first;
        Item element = first.element;
        first = first.next;
        if (first != null) first.prev = null;
        else last = null;
        oldFirst.next = null;  // for garbage collection
        total--;
        return element;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Node<Item> oldLast = last;
        Item element = last.element;
        last = last.prev;
        if (last != null) last.next = null;
        else first = null;
        oldLast.prev = null;
        total--;
        return element;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        /*
        if (isEmpty())

            throw new java.util.NoSuchElementException();

         */
        return new NodeIterator();
    }

    // junit testing (required)
    public static void main(String[] args) {
        Deque<Integer> myQueue = new Deque<Integer>();
        Deque<String> myQ2 = new Deque<String>();

        int testcase = 4;

        // Test case 3:
        if (testcase == 1) {
            StdOut.println("dequeue.isEmpty(): " + myQueue.isEmpty());
            myQueue.addLast(1);
            StdOut.println("after adding 1, dequeue.isEmpty(): " + myQueue.isEmpty());
            StdOut.println("removeFirst() : " + myQueue.removeFirst());
            StdOut.println("dequeue.isEmpty(): " + myQueue.isEmpty());
        }

        // Test the iterator of empty queue
        // 1. test the addFirst/addLast and iterator
        if (testcase == 2) {
            for (int i = 1; i < 10; i++)
                // myQueue.addFirst(i);
                myQueue.addLast(i);
            StdOut.println("total num of elements: " + myQueue.size());
            for (int k : myQueue)
                StdOut.println(k);
            StdOut.println("Test remove first:");
            int beforeRemove = myQueue.size();
            for (int j = 0; j < beforeRemove; j++)
                StdOut.print(myQueue.removeLast());
        }

        if (testcase == 3) {
            myQ2.addFirst("1");
            StdOut.println("adding 1 item, queue size = " + myQ2.size());
            myQ2.addLast("2");
            StdOut.println("adding 2 items, queue size = " + myQ2.size());
            myQ2.addFirst("3");
            myQ2.addLast("4");
            myQ2.addFirst("5");
            StdOut.println("adding 5 items, queue size = " + myQ2.size());
        }

        // test empty queue iterator
        if (testcase == 4) {
            myQueue.addLast(1);
            StdOut.println("removeLast() : " + myQueue.removeLast());
            for (int k : myQueue)
                StdOut.println(k);
        }
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
