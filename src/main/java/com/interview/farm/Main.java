package com.interview.farm;

import com.interview.farm.domain.Farm;

class Main {

    public static void main(String[] args) {
        Farm farm = new Farm();

        farm.giveBirth(null, "10", "Dolly");
        farm.giveBirth(null, "11", "Sally");
        farm.giveBirth("10", "100", "Marry");
        farm.giveBirth("11", "101", "Jack");

        farm.endLifeSpan("100");

        farm.printFarmData();
    }
}

