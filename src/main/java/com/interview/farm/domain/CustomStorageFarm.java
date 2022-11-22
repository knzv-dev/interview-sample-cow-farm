package com.interview.farm.domain;

import com.interview.farm.util.LinkedList;

import java.security.InvalidParameterException;

public class CustomStorageFarm implements Farm {
    private LinkedList<Cow> storage = new LinkedList<>();

    @Override
    public void giveBirth(String parentCowId, String childCowId, String childCowNickName) {
        if (parentCowId != null && parentCowId.isEmpty())
            throw new InvalidParameterException("parentId must not be empty");
        if (childCowId == null || childCowId.isEmpty())
            throw new InvalidParameterException("childCowId must not be null or empty");
        if (childCowNickName == null || childCowNickName.isEmpty())
            throw new InvalidParameterException("childCowNickName must not be null or empty");


        if (childCowId.equals(parentCowId))
            throw new InvalidParameterException("childCowId must not be equal to parentCowId");

        Cow newBornCow = new Cow()
                .setId(childCowId)
                .setNickname(childCowNickName);

        if (parentCowId != null) {
            Cow parentCow = storage.find(c -> c.getId().equals(parentCowId));

            if (parentCow == null) {
                throw new IllegalStateException(String.format("Parent cow with id %s does not exist", parentCowId));
            }


            if (parentCow.getChildren().contains(childCowId)) {
                throw new IllegalStateException(String.format("Cow with id %s already exists", childCowId));
            }

            parentCow.addChild(newBornCow.getId());
        }
        storage.add(newBornCow);

    }

    @Override
    public void endLifeSpan(String cowId) {
        if (cowId == null || cowId.isEmpty())
            throw new InvalidParameterException("cowId must not be null or empty");

        Cow cow = storage.find(el -> el.getId().equals(cowId));

        if (cow == null) throw new IllegalStateException(String.format("cow with id %s does not exists", cowId));
        storage.delete(cow);
    }

    @Override
    public void printFarmData() {
        if (storage == null) {
            System.out.println("No data exists yet");
        }

        System.out.println("id, nickname, children");

        for (Cow cow : storage) {
            System.out.println(cow.getId() + ", " + cow.getNickname() + ", [" + String.join(", ", cow.getChildren()) + "]");
        }
    }
}
