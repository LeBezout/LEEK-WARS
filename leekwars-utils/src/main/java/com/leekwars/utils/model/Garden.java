package com.leekwars.utils.model;

import java.util.List;
import java.util.Map;

/**
 * Infos du potager
 * @author Bezout
 */
public class Garden {
	private boolean farmer_enabled;
	private boolean team_enabled;
	
	private int solo_fights;
	private int solo_total_fights;
	
	private int farmer_fights;
	private int farmer_total_fights;
	
	private int team_fights;
	private int team_total_fights;
	
	private Map<String, LeekSummary[]> solo_enemies;
	private List<FarmerSummary> farmer_enemies;
	
	private Map<String, Integer> leek_fights;

	/**
	 * @return the farmer_enabled
	 */
	public boolean isFarmer_enabled() {
		return farmer_enabled;
	}

	/**
	 * @param pFarmer_enabled the farmer_enabled to set
	 */
	public void setFarmer_enabled(boolean pFarmer_enabled) {
		farmer_enabled = pFarmer_enabled;
	}

	/**
	 * @return the team_enabled
	 */
	public boolean isTeam_enabled() {
		return team_enabled;
	}

	/**
	 * @param pTeam_enabled the team_enabled to set
	 */
	public void setTeam_enabled(boolean pTeam_enabled) {
		team_enabled = pTeam_enabled;
	}

	/**
	 * @return the solo_fights
	 */
	public int getSolo_fights() {
		return solo_fights;
	}

	/**
	 * @param pSolo_fights the solo_fights to set
	 */
	public void setSolo_fights(int pSolo_fights) {
		solo_fights = pSolo_fights;
	}

	/**
	 * @return the solo_total_fights
	 */
	public int getSolo_total_fights() {
		return solo_total_fights;
	}

	/**
	 * @param pSolo_total_fights the solo_total_fights to set
	 */
	public void setSolo_total_fights(int pSolo_total_fights) {
		solo_total_fights = pSolo_total_fights;
	}

	/**
	 * @return the farmer_fights
	 */
	public int getFarmer_fights() {
		return farmer_fights;
	}

	/**
	 * @param pFarmer_fights the farmer_fights to set
	 */
	public void setFarmer_fights(int pFarmer_fights) {
		farmer_fights = pFarmer_fights;
	}

	/**
	 * @return the farmer_total_fights
	 */
	public int getFarmer_total_fights() {
		return farmer_total_fights;
	}

	/**
	 * @param pFarmer_total_fights the farmer_total_fights to set
	 */
	public void setFarmer_total_fights(int pFarmer_total_fights) {
		farmer_total_fights = pFarmer_total_fights;
	}

	/**
	 * @return the team_fights
	 */
	public int getTeam_fights() {
		return team_fights;
	}

	/**
	 * @param pTeam_fights the team_fights to set
	 */
	public void setTeam_fights(int pTeam_fights) {
		team_fights = pTeam_fights;
	}

	/**
	 * @return the team_total_fights
	 */
	public int getTeam_total_fights() {
		return team_total_fights;
	}

	/**
	 * @param pTeam_total_fights the team_total_fights to set
	 */
	public void setTeam_total_fights(int pTeam_total_fights) {
		team_total_fights = pTeam_total_fights;
	}

	/**
	 * @return the solo_enemies
	 */
	public Map<String, LeekSummary[]> getSolo_enemies() {
		return solo_enemies;
	}

	/**
	 * @param pSolo_enemies the solo_enemies to set
	 */
	public void setSolo_enemies(Map<String, LeekSummary[]> pSolo_enemies) {
		solo_enemies = pSolo_enemies;
	}

	/**
	 * @return the farmer_enemies
	 */
	public List<FarmerSummary> getFarmer_enemies() {
		return farmer_enemies;
	}

	/**
	 * @param pFarmer_enemies the farmer_enemies to set
	 */
	public void setFarmer_enemies(List<FarmerSummary> pFarmer_enemies) {
		farmer_enemies = pFarmer_enemies;
	}

	/**
	 * @return the leek_fights
	 */
	public Map<String, Integer> getLeek_fights() {
		return leek_fights;
	}

	/**
	 * @param pLeek_fights the leek_fights to set
	 */
	public void setLeek_fights(Map<String, Integer> pLeek_fights) {
		leek_fights = pLeek_fights;
	}

}
