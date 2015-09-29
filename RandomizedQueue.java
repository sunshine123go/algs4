package lab2;

/**
 * Created by wcf on 15-9-29.
 */
import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N;
    private Item[] items;
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        N = 0;
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < size(); i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    public boolean isEmpty() {
       return N == 0;
    }

    public int size() {
       return N;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        if (N == items.length) {
            resize(2 * items.length);
        }
        items[N++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int i = StdRandom.uniform(N);
        Item temp = items[i];
        items[i] = items[size() - 1];
        items[size() - 1] = null;
        N--;
        if (N > 0 && N == items.length / 4) {
            resize(items.length / 2);
        }
        return temp;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int i = StdRandom.uniform(N);
        return items[i];

    }

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }
    private class RandomIterator implements Iterator<Item> {
        private int current = N;
        private Item[] temps;
        public RandomIterator() {
            temps = items.clone();
//            if (!isEmpty()) {
//              StdRandom.shuffle(temps, 0, N - 1);
//            }
            Item temp;
            for (int i = 0; i < size(); i++) {
                int j = StdRandom.uniform(i + 1);
                temp = temps[j];
                temps[j] = temps[i];
                temps[i] = temp;
            }
        }
        public boolean hasNext() {
            return current > 0;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next() {
            if (current < 1) {
                throw new java.util.NoSuchElementException();
            }
            return temps[--current];
        }
    }
    public static void main(String[] args) {
        RandomizedQueue<Integer> a = new RandomizedQueue<Integer>();
        a.enqueue(1);
        a.enqueue(3);
        a.enqueue(5);
        for (int i : a) {
            StdOut.println(i);
        }
        StdOut.println("##############");
        for (int j : a) {
            StdOut.println(j);
        }
    }
}
