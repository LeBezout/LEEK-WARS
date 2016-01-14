package com.leekwars.utils.model;

/**
 * Classe générique pour stocker les infos d'une entité. (Leek ou Farmer)
 * @author Bezout
 */
public class Entity extends Identity {

	private int level = -1; // par pour le l'eleveur
	private int talent;
	
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param pLevel the level to set
	 */
	public void setLevel(int pLevel) {
		level = pLevel;
	}
	/**
	 * @return the talent
	 */
	public int getTalent() {
		return talent;
	}
	/**
	 * @param pTalent the talent to set
	 */
	public void setTalent(int pTalent) {
		talent = pTalent;
	}
}
