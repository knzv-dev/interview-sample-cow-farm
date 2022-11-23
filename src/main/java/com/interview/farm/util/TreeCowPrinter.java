package com.interview.farm.util;

import com.interview.farm.domain.Cow;

import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.function.Function;

public class TreeCowPrinter implements Printer<Iterable<Cow>> {

    private static final String ROOT_NODE_NAME = "farm";
    private final Writer outputStream;

    public TreeCowPrinter(Writer out) {
        outputStream = out;
    }

    @Override
    public void print(Iterable<Cow> cowIterable) throws IOException {
        TreeNode tree = new TreeNode(ROOT_NODE_NAME, new ArrayList<>());

        Map<String, String> cowNames = collectCowNames(cowIterable);

        Map<String, List<String>> relations = new TreeMap<>();

        cowIterable.forEach(cow -> {
            var children = new ArrayList<String>();
            cow.getChildren().forEach(childId -> {
                children.add(childId + " " + cowNames.get(childId));
            });
            relations.put(cow.getId() + " " + cow.getNickname(), children);
        });

        TreeUtils.buildTree(tree, relations);

        outputStream.write(tree.toString());
        outputStream.flush();
    }

    @Override
    public void print(String text) throws IOException {
        outputStream.write(text);
        outputStream.flush();
    }

    private static Map<String, String> collectCowNames(Iterable<? extends Cow> cowIterable) {
        Map<String, String> cowNames = new HashMap<>();
        cowIterable.forEach(cow -> {
            cowNames.put(cow.getId(), cow.getNickname());
        });
        return cowNames;
    }

    private static class TreeUtils {
        public static void collectLeaves(TreeNode root, List<TreeNode> result) {
            if (!root.children.isEmpty()) {
                for (var child : root.children) {
                    collectLeaves(child, result);
                }
            } else {
                result.add(root);
            }
        }

        public static void buildTree(TreeNode root, Map<String, List<String>> relations) {
            Function<Map.Entry<String, List<String>>, TreeNode> nodeFromEntry = entry -> {
                TreeNode node = new TreeNode(entry.getKey());
                for (var child : entry.getValue()) {
                    node.children.add(new TreeNode(child));
                }
                return node;
            };

            outer:
            for (var entry : relations.entrySet()) {
                TreeNode node = nodeFromEntry.apply(entry);

                List<TreeNode> leaves = new ArrayList<>();
                collectLeaves(root, leaves);

                // if one of the leaf is a new root, just append current children
                for (var leaf : leaves) {
                    if (node.value.equals(leaf.value)) {
                        leaf.children = node.children;
                        continue outer;
                    }
                }

                // if one or more children are roots, remove roots and add them as childs to keep hierarchy
                List<TreeNode> level2 = root.children;
                List<TreeNode> newChildren = new ArrayList<>();
                Iterator<TreeNode> currentTreeIterator = level2.iterator();
                while (currentTreeIterator.hasNext()) {
                    TreeNode parentNode = currentTreeIterator.next();
                    Iterator<TreeNode> newNodeChildrenIterator = node.children.iterator();
                    while (newNodeChildrenIterator.hasNext()) {
                        TreeNode newNodeChild = newNodeChildrenIterator.next();
                        if (newNodeChild.value.equals(parentNode.value)) {
                            newChildren.add(parentNode);
                            newNodeChildrenIterator.remove();
                            currentTreeIterator.remove();
                        }
                    }

                }


                if (!newChildren.isEmpty()) {
                    node.children = newChildren;
                }
                root.children.add(node);
            }
        }
    }

    private static class TreeNode {

        String value;
        List<TreeNode> children;

        public TreeNode(String value) {
            this(value, new ArrayList<>());
        }

        public TreeNode(String value, List<TreeNode> children) {
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
            buffer.append(value);
            buffer.append('\n');
            for (Iterator<TreeNode> it = children.iterator(); it.hasNext(); ) {
                TreeNode next = it.next();
                if (it.hasNext()) {
                    next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
                } else {
                    next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
                }
            }
        }
    }
}
