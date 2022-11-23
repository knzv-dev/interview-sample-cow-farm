package com.interview.farm.domain;

import com.interview.farm.util.LinkedList;
import com.interview.farm.util.Printer;
import com.interview.farm.util.TreeCowPrinter;

import java.io.OutputStreamWriter;

public class CustomStorageFarm extends AbstractFarm {

    private LinkedList<Cow> storage = new LinkedList<>();

    @Override
    protected Printer<Iterable<Cow>> getPrinter() {
        return new TreeCowPrinter(new OutputStreamWriter(System.out));
    }

    @Override
    protected Iterable<Cow> storageIterable() {
        return storage;
    }

    @Override
    protected boolean isStorageEmpty() {
        return storage.isEmpty();
    }

    @Override
    protected void deleteCow(Cow cow) {
        storage.delete(cow);
    }

    @Override
    protected void saveCow(Cow newBornCow) {
        storage.add(newBornCow);
    }

    @Override
    protected Cow findCowById(String cowId) {
        return storage.find(cow -> cow.getId().equals(cowId));
    }

    @Override
    protected Cow findParentByChildId(String cowId) {
        for (Cow cow : storage) {
            if (cow.getChildren().contains(cowId)) {
                return cow;
            }
        }
        return null;
    }
}
