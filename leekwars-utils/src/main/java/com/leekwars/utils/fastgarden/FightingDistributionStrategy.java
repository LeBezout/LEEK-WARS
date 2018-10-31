package com.leekwars.utils.fastgarden;

/**
 * Stratégies de répartition des combats pour le Fast Garden.
 * @author Bezout
 * @version 1.4.1
 * @since 1.4.1
 */
public enum FightingDistributionStrategy {

    /** Equilibrée entre toutes les entités - la limite est le nombre de combats disponibles. Exemple : 100 */
    BALANCED_TOTAL_LIMITED,
    /** Equilibrée entre toutes les entités - la limite par entité est le nombre de combats indiqué dans FastGardenParam#maxStartFights */
    BALANCED_PARAM_LIMITED
}
