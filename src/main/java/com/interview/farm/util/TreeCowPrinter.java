package com.interview.farm.util;

import com.interview.farm.domain.Cow;

import java.util.*;
import java.util.function.Function;

public class TreeCowPrinter {

    public static void print(Iterable<? extends Cow> cowIterable) {
        TreeNode tree = new TreeNode("farm", new ArrayList<>());

        Map<String, String> cowNames = collectCowNames(cowIterable);

        Map<String, List<String>> relations = new HashMap<>();
        cowIterable.forEach(cow -> {
            var children = new ArrayList<String>();
            cow.getChildren().forEach(childId -> {
                children.add(childId + " " + cowNames.get(childId));
            });
            relations.put(cow.getId() + " " + cow.getNickname(), children);
        });


        TreeUtils.buildTree(tree, relations);

        System.out.println(tree);
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

                for (var leaf : leaves) {
                    if (node.value.equals(leaf.value)) {
                        leaf.children = node.children;
                        continue outer;
                    }
                }

                List<TreeNode> level2 = root.children;
                for (String childId : entry.getValue()) {
                    for (TreeNode n : level2) {
                        if (n.value.equals(childId)) {
                            level2.remove(n);
                            node.children.removeIf(treeNode -> treeNode.value.equals(n.value));
                            node.children.add(n);
                            level2.add(node);
                            continue outer;
                        }
                    }
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
