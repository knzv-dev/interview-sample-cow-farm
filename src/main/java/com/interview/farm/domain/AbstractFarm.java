package com.interview.farm.domain;

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
    }

    @Override
    public void printFarmData() {
        if (isStorageEmpty()) {
            System.out.println("No data exists yet");
        }

        System.out.println("id, nickname, children");

        for (Cow cow : storageIterable()) {
            System.out.println(cow.getId() + ", " + cow.getNickname() + ", [" + String.join(", ", cow.getChildren()) + "]");
        }
    }

    protected abstract Iterable<? extends Cow> storageIterable();

    protected abstract boolean isStorageEmpty();

    protected abstract void deleteCow(Cow cow);

    protected abstract void saveCow(Cow newBornCow);

    protected abstract Cow findCowById(String cowId);

    private void validateNickname(String childCowNickName) {
        if (childCowNickName == null || childCowNickName.isEmpty())
            throw new InvalidParameterException("childCowNickName must not be null or empty");
    }

    private void validateCowId(String childCowId) {
        if (childCowId == null || childCowId.isEmpty())
            throw new InvalidParameterException("cow id must not be null or empty");
    }

    private void validateParentId(String parentCowId) {
        if (parentCowId != null && parentCowId.isEmpty()) {
            throw new InvalidParameterException("parent cow id must not be empty");
        }
    }
}
