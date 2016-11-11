package com.leekwars.utils.model;

/**
 * Infos d'un élément d'un des classements FUN
 * @author Bezout
 * @since 1.2
 */
public class RankingItem extends Identity {
    private long value;

    /**
     * @return the value
     */
    public long getValue() {
        return value;
    }
    /**
     * @param pValue the value to set
     */
    public void setValue(long pValue) {
        value = pValue;
    }
}
