package com.interview.farm.util;

import java.util.Iterator;
import java.util.function.Predicate;

public class LinkedList<T> implements Iterable<T> {
    private Node<T> head;

    public LinkedList() {
        head = null;
    }

    public <E extends T> void add(E cow) {
        if (head == null) {
            head = new Node<>(cow);
        } else {
            Node<T> tmp = head;
            while (tmp.hasNext()) {
                tmp = tmp.next();
            }

            tmp.add(cow);
        }
    }

    public T find(Predicate<T> predicate) {
        Node<T> tmp = head;
        while (tmp != null) {
            T value = tmp.getValue();
            if (predicate.test(value)) {
                return value;
            }

            tmp = tmp.next();
        }

        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T tmp = current.getValue();
                current = current.next();
                return tmp;
            }
        };
    }

    static class Node<T> {
        private Node<T> next;
        private T value;

        public Node(T value) {
            this.value = value;
        }

        public void add(T value) {
            this.next = new Node<>(value);
        }

        public boolean hasNext() {
            return next != null;
        }

        public T getValue() {
            return value;
        }

        public Node<T> next() {
            return next;
        }
    }
}


