package com.leekwars.utils.enums;

/**
 * Types de rang pour les classements (appelés "order" dans l'API)
 * @author Bezout
 * @since 1.2
 */
public enum RankType {
	/**
	 * Niveau de poireau
	 */
	LEVEL("level"),
	/**
	 * Niveau total d'éleveur
	 */
	TOTOAL_LEVEL("total-level"),
	/**
	 * Talent
	 */
	TALENT("talent"),
	/**
	 * Nom
	 */
	NAME("name"),
	;

	private String value;
	private RankType(final String pValue) {
		value = pValue;
	}

	/**
	 * @return value
	 */
	public String getValue() {
		return value;
	}
}
