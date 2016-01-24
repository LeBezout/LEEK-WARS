package com.leekwars.utils.model;

/**
 * Infos d'une composition d'équipe ennemie dans le potager
 * @author Bezout
 */
public class GardenEnemyTeamComposition extends Entity {
	private int total_level;
	private Entity team;
	
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
	 * @return the team
	 */
	public Entity getTeam() {
		return team;
	}
	/**
	 * @param pTeam the team to set
	 */
	public void setTeam(Entity pTeam) {
		team = pTeam;
	}
}
