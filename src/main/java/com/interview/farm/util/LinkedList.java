package com.interview.farm.util;

import java.util.Iterator;
import java.util.function.Predicate;

public class LinkedList<T> implements Iterable<T> {
    private Node<T> head;

    public LinkedList() {
        head = null;
    }

    public void add(T cow) {
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

    public T find(Predicate<? super T> predicate) {
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

    public void delete(T value) {
        Node<T> tmp = head;
        Node<T> prev = null;
        while (tmp != null) {
            if (tmp.getValue().equals(value)) {
                if (prev != null) {
                    prev.setNext(tmp.next());
                } else {
                    head = tmp.next();
                }
                break;
            }

            prev = tmp;
            tmp = tmp.next();
        }
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

    public boolean isEmpty() {
        return head == null || head.getValue() == null;
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

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }
}


