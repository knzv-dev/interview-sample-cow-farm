package com.interview.farm.util.tree;

import java.util.*;
import java.util.function.Function;

public class PrintableTreeNode<T> {

    private T value;

    private List<PrintableTreeNode<T>> children;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<PrintableTreeNode<T>> getChildren() {
        return children;
    }

    public void setChildren(List<PrintableTreeNode<T>> children) {
        this.children = children;
    }

    public List<PrintableTreeNode<T>> collectLeaves() {
        List<PrintableTreeNode<T>> result = new ArrayList<>();
        Stack<PrintableTreeNode<T>> unprocessed = new Stack<>();
        unprocessed.add(this);

        while (!unprocessed.isEmpty()) {
            var node = unprocessed.pop();
            if (node.children == null || node.children.isEmpty()) {
                result.add(node);
            } else {
                unprocessed.addAll(node.children);
            }
        }
        return result;
    }

    /*
     * Builds tree from map that contains relations between parents and child
     * For example:
     * For input map:
     * {
     *   "parent1": ["child1, "child2"],
     *   "child2": [child3, child4]
     * }
     *
     * method will return a valid tree
     *             parent1
     *            /        \
     *        child1      child2
     *                   /      \
     *               child3    child4
     *
     * if there is more trees that one
     * method will create an empty node to combine them;
     */
    public static <T> PrintableTreeNode<T> buildTreeFromMultipleRoots(Map<T, List<T>> relations, T combinedRootValue) {
        PrintableTreeNode<T> root = new PrintableTreeNode<>(combinedRootValue);

        Function<Map.Entry<T, List<T>>, PrintableTreeNode<T>> nodeFromEntry = entry -> {
            PrintableTreeNode<T> node = new PrintableTreeNode<>(entry.getKey());
            for (var child : entry.getValue()) {
                node.getChildren().add(new PrintableTreeNode<>(child));
            }
            return node;
        };

        outer:
        for (var entry : relations.entrySet()) {
            PrintableTreeNode<T> node = nodeFromEntry.apply(entry);

            List<PrintableTreeNode<T>> leaves = root.collectLeaves();

            // if one of the leaf is a new root, just append current children
            for (var leaf : leaves) {
                if (node.getValue().equals(leaf.getValue())) {
                    leaf.setChildren(node.getChildren());
                    continue outer;
                }
            }

            // if one or more children are roots, remove roots and add them as childs to keep hierarchy
            List<PrintableTreeNode<T>> level2 = root.getChildren();
            List<PrintableTreeNode<T>> newChildren = new ArrayList<>();
            Iterator<PrintableTreeNode<T>> currentTreeIterator = level2.iterator();
            while (currentTreeIterator.hasNext()) {
                PrintableTreeNode<T> parentNode = currentTreeIterator.next();
                Iterator<PrintableTreeNode<T>> newNodeChildrenIterator = node.getChildren().iterator();
                while (newNodeChildrenIterator.hasNext()) {
                    PrintableTreeNode<T> newNodeChild = newNodeChildrenIterator.next();
                    if (newNodeChild.getValue().equals(parentNode.getValue())) {
                        newChildren.add(parentNode);
                        newNodeChildrenIterator.remove();
                        currentTreeIterator.remove();
                    }
                }

            }


            if (!newChildren.isEmpty()) {
                node.setChildren(newChildren);
            }
            root.getChildren().add(node);
        }
        return root;
    }


    public PrintableTreeNode(T value) {
        this(value, new ArrayList<>());
    }

    public PrintableTreeNode(T value, List<PrintableTreeNode<T>> children) {
        this.value = value;
        this.children = children;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(value.toString());
        buffer.append('\n');
        for (Iterator<PrintableTreeNode<T>> it = children.iterator(); it.hasNext(); ) {
            PrintableTreeNode<T> next = it.next();
            if (it.hasNext()) {
                next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }
}
