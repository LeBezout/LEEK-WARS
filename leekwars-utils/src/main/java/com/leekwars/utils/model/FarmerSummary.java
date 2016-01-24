package com.leekwars.utils.model;

/**
 * Informations minimales d'un éleveur (par exemple dans le potager)
 * @author Bezout
 */
public class FarmerSummary extends Entity {
	// "id":693,"name":"Foudge","avatar_changed":1403561524,"talent":2198,"total_level":1184,"leek_count":4
	private int total_level;
	private int leek_count;
	
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
	 * @return the leek_count
	 */
	public int getLeek_count() {
		return leek_count;
	}
	/**
	 * @param pLeek_count the leek_count to set
	 */
	public void setLeek_count(int pLeek_count) {
		leek_count = pLeek_count;
	}
}
