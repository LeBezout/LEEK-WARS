package com.leekwars.utils.io;

import com.leekwars.utils.model.SimpleJSONResponse;
import com.leekwars.utils.model.Trophy;

import java.util.Map;

/**
 * Informations recues suite à un appel à "trophy/get-farmer-trophies".
 * @author Bezout
 */
public class GetFarmerTrophiesJSONResponse extends SimpleJSONResponse {

    private Map<String, Trophy> trophies;
    private int count;
    private int total;
    private String farmer_name;

    public Map<String, Trophy> getTrophies() {
        return trophies;
    }

    public void setTrophies(Map<String, Trophy> pTrophies) {
        trophies = pTrophies;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int pCount) {
        count = pCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int pTotal) {
        total = pTotal;
    }

    public String getFarmerName() {
        return farmer_name;
    }

    public void setFarmerName(String pFarmer_name) {
        farmer_name = pFarmer_name;
    }
}
