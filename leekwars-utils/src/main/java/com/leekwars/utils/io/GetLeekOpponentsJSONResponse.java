package com.leekwars.utils.io;

import com.leekwars.utils.model.LeekSummary;
import com.leekwars.utils.model.SimpleJSONResponse;

/**
 * Informations du potager suite à un appel à "garden/get-leek-opponents"
 * @author Bezout
 * @version 1.1
 * @since 1.1
 */
public class GetLeekOpponentsJSONResponse extends SimpleJSONResponse {
    private LeekSummary[] opponents;

    /**
     * @return tableau des ennemis possibles
     */
    public LeekSummary[] getOpponents() {
        return opponents;
    }

    /**
     * @param pOpponents tableau
     */
    public void setOpponents(LeekSummary[] pOpponents) {
        opponents = pOpponents;
    }
}
