package com.interview.farm.domain;

import com.interview.farm.util.LinkedList;

import java.security.InvalidParameterException;

public class Farm {
    private LinkedList<Cow> list = new LinkedList<>();

    public void giveBirth(String parentCowId, String childCowId, String childCowNickName) {
        if (parentCowId != null && parentCowId.length() == 0)
            throw new InvalidParameterException("parentId must not be empty");
        if (childCowId == null || childCowId.length() == 0)
            throw new InvalidParameterException("childCowId must not be null or empty");
        if (childCowNickName == null || childCowNickName.length() == 0)
            throw new InvalidParameterException("childCowNickName must not be null or empty");


        if (childCowId.equals(parentCowId))
            throw new InvalidParameterException("childCowId must not be equal to parentCowId");

        Cow cow = new Cow()
                .setParentId(parentCowId)
                .setId(childCowId)
                .setNickname(childCowNickName);

        list.add(cow);

    }

    public void endLifeSpan(String cowId) {
        if (cowId == null || cowId.length() == 0)
            throw new InvalidParameterException("cowId must not be null or empty");

        Cow cow = list.find(el -> el.getId().equals(cowId));

        if (cow == null) throw new IllegalStateException(String.format("cow with id %s does not exists", cowId));
        if (!cow.isAlive())
            throw new IllegalStateException(String.format("lifespan of cow with id %s already ended", cowId));

        cow.setIsAlive(false);
    }

    public void printFarmData() {
        if (list == null) {
            System.out.println("No data exists yet");
        }

        System.out.println("id, parentId, nickname, isAlive");

        for (Cow cow : list) {
            System.out.println(cow.getId() + ", " + cow.getParentId() + ", " + cow.getNickname() + ", " + cow.isAlive());
        }
    }
}
