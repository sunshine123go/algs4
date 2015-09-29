package lab2;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * Created by wcf on 15-9-29.
 */
public class Deque<Item> implements Iterable<Item> {

    private int N;
    private Node first;
    private Node last;
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    public Deque() {
        N = 0;
        first = null;
        last = first;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        if (isEmpty()) {
            last = first;
        } else {
            first.next = oldfirst;
            oldfirst.prev = first;
        }
        N++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
            last.prev = oldlast;
        }
        N++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        N--;
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        } else {    //if first is empty, them first.prev is Invalid.
            first.prev = null;
        }
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        N--;
        Item item = last.item;
        last = last.prev;
        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> a = new Deque<Integer>();
        a.addFirst(0);
        a.removeFirst();
        a.addLast(1);
        for (int i : a) {
            StdOut.println(i);
        }
    }
}
