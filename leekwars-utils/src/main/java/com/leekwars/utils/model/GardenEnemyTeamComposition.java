package com.leekwars.utils.model;

/**
 * Infos d'une composition d'équipe ennemie dans le potager
 * Depuis v1.92 obtenu avec garden/get-composition-opponents
 * @author Bezout
 * @version 1.1
 */
public class GardenEnemyTeamComposition extends Entity {
	private int total_level;
	private long total_power;
	private long team;

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
	 * @return the total_power
	 */
	public long getTotal_power() {
		return total_power;
	}
	/**
	 * @param pTotal_power the total_power to set
	 */
	public void setTotal_level(long pTotal_power) {
		total_power = pTotal_power;
	}
	/**
	 * @return the team
	 */
	public long getTeam() {
		return team;
	}
	/**
	 * @param pTeam the team to set
	 */
	public void setTeam(long pTeam) {
		team = pTeam;
	}
	
	@Override
	public String toString() {
		return "[" + getId() + "] " + getName();
	}
}
