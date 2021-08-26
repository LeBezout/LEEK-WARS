package com.leekwars.utils.io;

import com.leekwars.utils.model.SimpleJSONResponse;
import com.leekwars.utils.model.Trophy;

import java.util.List;
import java.util.Map;

/**
 * Informations recues suite à un appel à "trophy/get-farmer-trophies".
 * @author Bezout
 * @version API v2.25
 */
public class GetFarmerTrophiesJSONResponse extends SimpleJSONResponse {

    private List<Trophy> trophies;
    private int count;
    private int total;

    public List<Trophy> getTrophies() {
        return trophies;
    }

    public void setTrophies(List<Trophy> pTrophies) {
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
}
