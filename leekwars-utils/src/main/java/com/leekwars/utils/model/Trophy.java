package com.leekwars.utils.model;

import com.leekwars.utils.enums.TrophyCategory;

/**
 * Infos d'un trophée
 * @author Bezout
 */
public class Trophy extends Identity {
    /* Nouvelle version since 2.25 :
      "id": 190,
      "code": "inclusive",
      "name": "Inclusif",
      "description": "Avoir 10 IA inclues entre elles",
      "category": 7,
      "unlocked": true,
      "date": 1629836133,
      "threshold": "",
      "index": 16408,
      "in_fight": 0,
      "rarity": 0.00036868427,
      "total": 23,
      "points": 30,
      "difficulty": 2,
      "habs": 90000
     */
    private String code;
    private String description;
    private long category;
    private boolean unlocked;
    private long date;
    private String threshold;
    private long habs;

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

    public long getHabs() {
        return habs;
    }
    public void setHabs(long pHabs) {
        habs = pHabs;
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
