package com.interview.farm.domain;

import com.interview.farm.util.LinkedList;

import java.security.InvalidParameterException;

public class CustomStorageFarm implements Farm {
    private LinkedList<Cow> list = new LinkedList<>();

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

        if (list.find(c -> c.getId().equals(childCowId)) != null) {
            throw new IllegalStateException(String.format("cow with id %s already exists", childCowId));
        }

        list.add(cow);

    }

    @Override
    public void endLifeSpan(String cowId) {
        if (cowId == null || cowId.isEmpty())
            throw new InvalidParameterException("cowId must not be null or empty");

        Cow cow = list.find(el -> el.getId().equals(cowId));

        if (cow == null) throw new IllegalStateException(String.format("cow with id %s does not exists", cowId));
        list.delete(cow);
    }

    @Override
    public void printFarmData() {
        if (list == null) {
            System.out.println("No data exists yet");
        }

        System.out.println("id, parentId, nickname");

        for (Cow cow : list) {
            System.out.println(cow.getId() + ", " + cow.getParentId() + ", " + cow.getNickname());
        }
    }
}
