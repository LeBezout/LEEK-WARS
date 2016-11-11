package com.leekwars.utils.model;

/**
 * Infos d'un des classements FUN
 * @author Bezout
 * @since 1.2
 */
public class FunRanking {
    private String title;
    private String value;
    private String value_type; // money/number/distance => tjs des entiers donc
    private RankingItems ranking;

    public String getTitle() {
        return title;
    }
    public void setTitle(String ptitle) {
        title = ptitle;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String pvalue) {
        value = pvalue;
    }

    public String getValue_type() {
        return value_type;
    }
    public void setValue_type(String pvalue_type) {
        value_type = pvalue_type;
    }

    public RankingItems getRanking() {
        return ranking;
    }
    public void setRanking(RankingItems pranking) {
        ranking = pranking;
    }
}
