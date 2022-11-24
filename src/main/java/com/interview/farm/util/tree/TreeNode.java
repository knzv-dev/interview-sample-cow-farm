package com.interview.farm.util.tree;

import java.util.*;

public class TreeNode<T> {

    private T value;

    private List<TreeNode<T>> children;

    public TreeNode(T value) {
        this(value, new ArrayList<>());
    }

    public TreeNode(T value, List<TreeNode<T>> children) {
        this.value = value;
        this.children = children;
    }

    /*
     * Builds tree from map that contains relations between parents and child,
     * map may contain several trees they will be combined under common root
     *
     * For example:
     * For input map is:
     * {
     *   "parent1": ["child1, "child2"],
     *   "child2": [child3, child4],
     *   "parent2": [child5, child6, child7]
     * }
     *
     * method will return a valid tree
     *                   {commonRootValue}
     *                  /                  \
     *             parent1                  parent2
     *            /        \             /     |     \
     *        child1      child2    child5   child6   child7
     *                   /      \
     *               child3    child4
     *
     */
    public static <T> TreeNode<T> buildTreeFromMultipleRoots(Map<T, List<T>> relations, T commonRootValue) {
        TreeNode<T> root = new TreeNode<>(commonRootValue);

        for (var entry : relations.entrySet()) {
            TreeNode<T> node = new TreeNode<>(entry.getKey());
            for (var child : entry.getValue()) {
                node.children.add(new TreeNode<>(child));
            }

            TreeNode<T> leaf = root.getLeafByValue(node.value);
            if (leaf != null) {
                // if one of the leaves is a new node, just append new children
                leaf.children = node.children;
            } else {
                // if one or more roots are children of new node we should rebase them to be a child of new node
                List<TreeNode<T>> newChildren = new ArrayList<>();
                for (TreeNode<T> l2Node : root.children) {
                    for (TreeNode<T> child : node.children) {
                        if (l2Node.value.equals(child.value)) {
                            newChildren.add(l2Node);
                        }
                    }
                }

                if (!newChildren.isEmpty()) {
                    for (TreeNode<T> n : newChildren) {
                        root.children.remove(n);
                        node.children.removeIf(child -> child.value.equals(n.value));
                        node.children.add(n);
                    }
                }

                root.children.add(node);
            }

        }
        return root;
    }

    public T getValue() {
        return value;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public List<TreeNode<T>> leaves() {
        List<TreeNode<T>> result = new ArrayList<>();
        Stack<TreeNode<T>> unprocessed = new Stack<>();
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

    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    private TreeNode<T> getLeafByValue(T value) {
        return leaves().stream()
                .filter(node -> node.value.equals(value))
                .findFirst()
                .orElse(null);
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(value.toString());
        buffer.append('\n');
        for (Iterator<TreeNode<T>> it = children.iterator(); it.hasNext(); ) {
            TreeNode<T> next = it.next();
            if (it.hasNext()) {
                next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }
}
