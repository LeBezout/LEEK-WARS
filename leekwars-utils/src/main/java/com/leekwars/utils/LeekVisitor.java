package com.leekwars.utils;

import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.model.LeekSummary;

/**
 * Interface décrivant un visiteur pour itérer sur les poireaux d'un éleveur par exemple.
 * @author Bezout
 * @since 1.2
 */
public interface LeekVisitor {

    /**
     * Avant d'itérer sur les poireaux
     */
    void beforeLeeks();

    /**
     * Visite du poireau
     * @param pLeek LeekSummary
     * @throws LWException
     */
    void onLeek(final LeekSummary pLeek) throws LWException;

    /**
     * Après avoir itéré sur sur tous les poireaux
     */
    void afterLeeks();
}
