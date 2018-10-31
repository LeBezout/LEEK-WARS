package com.leekwars.utils.enums;

/**
 * Catégories des trophées
 * @since 1.1
 * @author Bezout
 */
public enum TrophyCategory {
	/**
	 * Général = 1
	 */
	GENERAL(1),
	/**
	 * Combat = 2
	 */
	FIGHT(2),
	/**
	 * Tournois = 3
	 */
	TOURNAMENTS(3),
	/**
	 * Fun = 4
	 */
	FUN(4),
	/**
	 * Social = 5
	 */
	SOCIAL(5),
	/**
	 * Bonus = 6
	 */
	BONUS(6),
	/**
	 * Code = 7
	 */
	CODE(7),
	/**
	 * Achat = 8
	 */
	SHOPPING(8)
	;
	private int code;
	private TrophyCategory(final int pCode) {
		code = pCode;
	}

	/**
	 * @return code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param pCode
	 * @return element de l'enum
	 */
	public TrophyCategory fromCode(final int pCode) {
		switch (pCode) {
			case 1 : return GENERAL;
			case 2 : return FIGHT;
			case 3 : return TOURNAMENTS;
			case 4 : return FUN;
			case 5 : return SOCIAL;
			case 6 : return BONUS;
			case 7 : return CODE;
			case 8 : return SHOPPING;
			default : return null;
		}
	}
}
