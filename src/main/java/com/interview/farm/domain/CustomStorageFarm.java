package com.interview.farm.domain;

import com.interview.farm.util.LinkedList;

public class CustomStorageFarm extends AbstractFarm {

    private LinkedList<Cow> storage = new LinkedList<>();

    @Override
    protected Iterable<? extends Cow> storageIterable() {
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
}
