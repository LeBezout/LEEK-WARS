package com.leekwars.utils.model;

/**
 * Objet intermédiaire qui contient les items d'un des classements FUN
 * @author Bezout
 * @since 1.2
 */
public class RankingItems {
    private RankingItem[] ranking;
    private int farmer_rank;
    private long farmer_value;

    /**
     * @return tableau de RankingItem
     */
    public RankingItem[] getRankings() {
        return ranking;
    }

    /**
     * @param pRankings tableau de RankingItem
     */
    public void setRankingItem(final RankingItem[] pRankings) {
        ranking = pRankings;
    }

    public int getFarmer_rank() {
        return farmer_rank;
    }
    public void setFarmer_rank(int pfarmer_rank) {
        farmer_rank = pfarmer_rank;
    }

    public long getFarmer_value() {
        return farmer_value;
    }
    public void setFarmer_value(long pfarmer_value) {
        farmer_value = pfarmer_value;
    }
}
