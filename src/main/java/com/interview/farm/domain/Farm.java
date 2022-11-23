package com.interview.farm.domain;

public interface Farm {

    void giveBirth(String parentCowId, String childCowId, String childCowNickName);

    void endLifeSpan(String cowId);

    void printFarmData();
}
