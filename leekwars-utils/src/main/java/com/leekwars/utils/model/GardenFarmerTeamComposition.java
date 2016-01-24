package com.leekwars.utils.model;

/**
 * Infos d'une composition d'équipe de l'éleveur dans le potager
 * @author Bezout
 */
public class GardenFarmerTeamComposition extends Entity {
	private int total_level;
	private int fights;
	
	/**
	 * @return the total_level
	 */
	public int getTotal_level() {
		return total_level;
	}
	/**
	 * @param pTotal_level the total_level to set
	 */
	public void setTotal_level(int pTotal_level) {
		total_level = pTotal_level;
	}
	/**
	 * @return the fights
	 */
	public int getFights() {
		return fights;
	}
	/**
	 * @param pFights the fights to set
	 */
	public void setFights(int pFights) {
		fights = pFights;
	}
	
}
