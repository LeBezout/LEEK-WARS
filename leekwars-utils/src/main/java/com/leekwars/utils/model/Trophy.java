package com.leekwars.utils.model;

import com.leekwars.utils.enums.TrophyCategory;

/**
 * Infos d'un trophée
 * @author Bezout
 */
public class Trophy extends Identity {
    private String code;
    private String description;
    private long category;
    private boolean unlocked;
    private long date;
    private String progression;
    private String threshold;

    public String getCode() {
        return code;
    }

    public void setCode(String pCode) {
        code = pCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public long getCategory() {
        return category;
    }

    public void setCategory(long pCategory) {
        category = pCategory;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlock) {
        unlocked = unlock;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long pDate) {
        date = pDate;
    }

    public String getProgression() {
        return progression;
    }

    public void setProgression(String pProgression) {
        progression = pProgression;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String pThreshold) {
        threshold = pThreshold;
    }

    /**
     * @return true si c'est un trophée bonus (hors classement)
     */
    public boolean isBonus() {
        return category == TrophyCategory.BONUS.getCode();
    }
}
