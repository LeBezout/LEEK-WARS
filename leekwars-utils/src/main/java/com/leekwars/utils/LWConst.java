package com.leekwars.utils;

import java.nio.charset.StandardCharsets;

/**
 * Constantes diverses
 * @author Bezout
 * @since 1.4.1
 */
public abstract class LWConst {
    /**
     * Charset par défaut à utiliser
     */
    public static final String DEFAULT_ENCODING = StandardCharsets.UTF_8.toString();
    /**
     * Tabulation (pour les logs)
     */
    public static final String STR_TAB = "\t";
    /**
     * Ligne de séparation pour les logs
     */
    public static final String LOG_SEPARATOR = "--------------------------------------------------------------------------------------";
    /**
     * Ligne de séparation pour les logs de type warning
     */
    public static final String LOG_WARN_SEPARATOR = "*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*";
}
