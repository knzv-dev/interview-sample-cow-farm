package com.interview.farm.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StandardLibraryStorageFarm extends AbstractFarm {

    private Map<String, Cow> storage = new ConcurrentHashMap<>();

    @Override
    protected Iterable<? extends Cow> storageIterable() {
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
}
