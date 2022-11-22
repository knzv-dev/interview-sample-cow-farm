package com.interview.farm.domain;

import java.security.InvalidParameterException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StandardLibraryStorageFarm implements Farm {

    private Map<String, Cow> storage = new ConcurrentHashMap<>();

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

        Cow cow = new Cow()
                .setParentId(parentCowId)
                .setId(childCowId)
                .setNickname(childCowNickName);

        if (storage.containsKey(childCowId)) {
            throw new IllegalStateException(String.format("cow with id %s already exists", childCowId));
        }

        storage.put(childCowId, cow);
    }

    @Override
    public void endLifeSpan(String cowId) {
        if (cowId == null || cowId.isEmpty())
            throw new InvalidParameterException("cowId must not be null or empty");

        Cow cow = storage.get(cowId);

        if (cow == null) throw new IllegalStateException(String.format("cow with id %s does not exists", cowId));
        storage.remove(cow.getId());
    }

    @Override
    public void printFarmData() {
        if (storage.isEmpty()) {
            System.out.println("No data exists yet");
        }

        System.out.println("id, parentId, nickname, isAlive");

        for (Cow cow : storage.values()) {
            System.out.println(cow.getId() + ", " + cow.getParentId() + ", " + cow.getNickname());
        }
    }
}
