package com.interview.farm.domain;

import com.interview.farm.util.Printer;
import com.interview.farm.util.TreeCowPrinter;

import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StandardLibraryStorageFarm extends AbstractFarm {

    private Map<String, Cow> storage = new ConcurrentHashMap<>();

    @Override
    protected Printer<Iterable<Cow>> getPrinter() {
        return new TreeCowPrinter(new OutputStreamWriter(System.out));
    }

    @Override
    protected Iterable<Cow> storageIterable() {
        return storage.values();
    }

    @Override
    protected boolean isStorageEmpty() {
        return storage.isEmpty();
    }

    @Override
    protected void deleteCow(Cow cow) {
        storage.remove(cow.getId());
    }

    @Override
    protected void saveCow(Cow newBornCow) {
        storage.put(newBornCow.getId(), newBornCow);
    }

    @Override
    protected Cow findCowById(String cowId) {
        return storage.get(cowId);
    }

    @Override
    protected Cow findParentByChildId(String cowId) {
        for (Cow cow : storage.values()) {
            if (cow.getChildren().contains(cowId)) {
                return cow;
            }
        }
        return null;
    }
}
