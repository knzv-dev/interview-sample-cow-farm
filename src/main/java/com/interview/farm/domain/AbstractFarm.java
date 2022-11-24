package com.interview.farm.domain;

import com.interview.farm.util.Printer;

import java.io.IOException;
import java.security.InvalidParameterException;

public abstract class AbstractFarm implements Farm {

    @Override
    public void giveBirth(String parentCowId, String childCowId, String childCowNickName) {
        validateParentId(parentCowId);
        validateCowId(childCowId);
        validateNickname(childCowNickName);


        if (childCowId.equals(parentCowId))
            throw new InvalidParameterException("childCowId must not be equal to parentCowId");

        Cow newBornCow = new Cow()
                .setId(childCowId)
                .setNickname(childCowNickName);

        if (parentCowId != null) {
            Cow parentCow = findCowById(parentCowId);

            if (parentCow == null) {
                throw new IllegalStateException(String.format("Parent cow with id %s does not exist", parentCowId));
            }

            if (parentCow.getChildren().contains(childCowId)) {
                throw new IllegalStateException(String.format("Cow with id %s already exists", childCowId));
            }

            parentCow.addChild(newBornCow.getId());
        }

        saveCow(newBornCow);
    }

    @Override
    public void endLifeSpan(String cowId) {
        validateCowId(cowId);

        Cow cow = findCowById(cowId);

        if (cow == null) throw new IllegalStateException(String.format("cow with id %s does not exists", cowId));
        deleteCow(cow);
        Cow parent = findParentByChildId(cowId);

        if(parent != null) {
            parent.removeChild(cowId);
            for (String childCowId : cow.getChildren()) {
                parent.addChild(childCowId);
            }
        }
    }

    @Override
    public void printFarmData() {
        try {
            if (isStorageEmpty()) {
                getPrinter().print("No data");
            }
            getPrinter().print(storageIterable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    protected abstract Printer<Iterable<Cow>> getPrinter();

    protected abstract Iterable<Cow> storageIterable();

    protected abstract boolean isStorageEmpty();

    protected abstract void deleteCow(Cow cow);

    protected abstract void saveCow(Cow newBornCow);

    protected abstract Cow findCowById(String cowId);

    protected abstract Cow findParentByChildId(String cowId);

    private void validateParentId(String parentCowId) {
        if (parentCowId != null && parentCowId.isBlank()) {
            throw new InvalidParameterException("parent cow id must not be empty");
        }
    }

    private void validateCowId(String childCowId) {
        if (childCowId == null || childCowId.isBlank()) {
            throw new InvalidParameterException("cow id must not be null or empty");
        }
    }

    private void validateNickname(String childCowNickName) {
        if (childCowNickName == null || childCowNickName.isBlank()) {
            throw new InvalidParameterException("childCowNickName must not be null or empty");
        }
    }
}
