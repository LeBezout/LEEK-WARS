package com.leekwars.utils.io;

import com.leekwars.utils.model.SimpleJSONResponse;

/**
 * Informations recues si l'on demande un classement
 * @since 1.2
 * @author Bezout
 */
public class GetRankJSONResponse extends SimpleJSONResponse {
    private long rank;

    /**
     * @return the rank
     */
    public long getRank() {
        return rank;
    }
    /**
     * @param pRank the rank to set
     */
    public void setRank(long pRank) {
        rank = pRank;
    }
}
