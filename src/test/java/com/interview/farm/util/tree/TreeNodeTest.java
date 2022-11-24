package com.interview.farm.util.tree;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TreeNodeTest {

    @Test
    public void leaves_should_return_leaves_of_the_tree() {
        TreeNode<String> leaf1 = new TreeNode<>("leaf1");
        TreeNode<String> leaf2 = new TreeNode<>("leaf2");
        TreeNode<String> leaf3 = new TreeNode<>("leaf3");
        TreeNode<String> subRoot = new TreeNode<>("subRoot");
        TreeNode<String> root = new TreeNode<>("root");

        root.getChildren().add(leaf1);
        root.getChildren().add(subRoot);
        subRoot.getChildren().add(leaf2);
        subRoot.getChildren().add(leaf3);

        List<TreeNode<String>> leaves = root.leaves();
        assertEquals(3, leaves.size());
        assertTrue(leaves.contains(leaf1));
        assertTrue(leaves.contains(leaf2));
        assertTrue(leaves.contains(leaf3));
    }

    @Test
    public void buildTreeFromMultipleRoots_should_build_tree_with_one_node_on_empty_map() {
        Map<String, List<String>> source = new HashMap<>();

        TreeNode<String> root = TreeNode.buildTreeFromMultipleRoots(source, "RootName");

        assertTrue(root.getChildren().isEmpty());
        assertEquals("RootName", root.getValue());
    }

    @Test
    public void buildTreeFromMultipleRoots_should_build_tree_from_a_map_1() {
        // to order alphabetically
        Map<String, List<String>> source = new TreeMap<>();
        source.put("a", List.of("aa", "ab", "ac"));
        source.put("b", List.of("ba"));
        source.put("c", List.of("ca"));
        source.put("ab", List.of("aba", "abb", "abc"));
        source.put("ca", List.of("caa", "cab", "cac"));

        TreeNode<String> root = TreeNode.buildTreeFromMultipleRoots(source, "Letters");

        assertEquals("Letters", root.getValue());
        assertEquals(3, root.getChildren().size());

        TreeNode<String> a = root.getChildren().get(0);
        assertEquals("a", a.getValue());
        assertEquals(3, a.getChildren().size());

        TreeNode<String> b = root.getChildren().get(1);
        assertEquals("b", b.getValue());
        assertEquals(1, b.getChildren().size());

        TreeNode<String> c = root.getChildren().get(2);
        assertEquals("c", c.getValue());
        assertEquals(1, c.getChildren().size());

        TreeNode<String> ab = a.getChildren().get(1);
        assertEquals("ab", ab.getValue());
        assertEquals(3, ab.getChildren().size());

        TreeNode<String> abc = ab.getChildren().get(2);
        assertEquals("abc", abc.getValue());
        assertEquals(0, abc.getChildren().size());
    }

}