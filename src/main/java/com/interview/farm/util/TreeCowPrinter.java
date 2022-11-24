package com.interview.farm.util;

import com.interview.farm.domain.Cow;
import com.interview.farm.util.tree.TreeNode;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class TreeCowPrinter implements Printer<Iterable<Cow>> {

    private final Writer outputStream;

    public TreeCowPrinter(Writer out) {
        outputStream = out;
    }

    @Override
    public void print(Iterable<Cow> cowIterable) throws IOException {
        Map<String, String> cowNames = collectCowNames(cowIterable);
        Map<String, List<String>> relations = new TreeMap<>();

        cowIterable.forEach(cow -> {
            var children = new ArrayList<String>();
            cow.getChildren().forEach(childId -> {
                children.add(childId + " " + cowNames.get(childId));
            });
            relations.put(cow.getId() + " " + cow.getNickname(), children);
        });

        TreeNode<String> treeNode = TreeNode.buildTreeFromMultipleRoots(relations, "Farm");

        outputStream.write(treeNode.toString());
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
}
