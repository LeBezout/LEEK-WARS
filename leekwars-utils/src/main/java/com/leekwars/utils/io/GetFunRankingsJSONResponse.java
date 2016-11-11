package com.leekwars.utils.io;

import com.leekwars.utils.model.FunRanking;
import com.leekwars.utils.model.SimpleJSONResponse;

/**
 * Informations recues suite à un appel à "ranking/fun".
 * @since 1.2
 * @author Bezout
 */
public class GetFunRankingsJSONResponse extends SimpleJSONResponse {
    private FunRanking[] rankings;

    /**
     * @return tableau de FunRanking
     */
    public FunRanking[] getRankings() {
        return rankings;
    }

    /**
     * @param pRankings tableau de FunRanking
     */
    public void setFunRanking(final FunRanking[] pRankings) {
        rankings = pRankings;
    }
}