package com.interview.farm;

import com.interview.farm.domain.CustomStorageFarm;
import com.interview.farm.domain.Farm;
import com.interview.farm.domain.StandardLibraryStorageFarm;

class Main {

    public static void main(String[] args) {

        System.out.println("=============cs============");
        Farm csFarm = new CustomStorageFarm();

        csFarm.giveBirth(null, "10", "Dolly");
        csFarm.giveBirth(null, "11", "Sally");
        csFarm.giveBirth("10", "100", "Marry");
        csFarm.giveBirth("11", "101", "Nelly");
        csFarm.giveBirth("11", "102", "Kelly");
        csFarm.giveBirth("11", "103", "Belly");

        csFarm.endLifeSpan("100");

        csFarm.printFarmData();


        System.out.println("=============sls============");
        Farm slsFarm = new StandardLibraryStorageFarm();

        slsFarm.giveBirth(null, "10", "Dolly");
        slsFarm.giveBirth(null, "11", "Sally");
        slsFarm.giveBirth("10", "100", "Marry");
        slsFarm.giveBirth("11", "101", "Nelly");
        slsFarm.giveBirth("11", "102", "Kelly");
        slsFarm.giveBirth("11", "103", "Belly");

        slsFarm.endLifeSpan("100");

        slsFarm.printFarmData();
    }
}

