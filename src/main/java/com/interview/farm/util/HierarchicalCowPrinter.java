package com.interview.farm.util;

import com.interview.farm.domain.Cow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HierarchicalCowPrinter {

    public static void print(Iterable<? extends Cow> cowIterable) {
        TreeNode tree = new TreeNode("farm", new ArrayList<>());

        outer:
        for (Cow cow : cowIterable) {

            TreeNode node = new TreeNode(cow.getId());
            for (var child : cow.getChildren()) {
                node.children.add(new TreeNode(child));
            }

            List<TreeNode> leaves = new ArrayList<>();
            TreeUtils.collectLeaves(tree, leaves);

            for (var leaf : leaves) {
                if (node.value.equals(leaf.value)) {
                    leaf.children = node.children;
                    continue outer;
                }
            }

            List<TreeNode> level2 = tree.children;
            for (String child : cow.getChildren()) {
                for (TreeNode n : level2) {
                    if (n.value.equals(child)) {
                        level2.remove(n);
                        node.children.removeIf(treeNode -> treeNode.value.equals(n.value));
                        node.children.add(n);
                        level2.add(node);
                        continue outer;
                    }
                }
            }

            tree.children.add(node);
        }

        System.out.println(tree);
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
    }

    public static class TreeNode {

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
