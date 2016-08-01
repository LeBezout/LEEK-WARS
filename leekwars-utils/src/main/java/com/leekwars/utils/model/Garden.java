package com.leekwars.utils.model;

import java.util.List;
import java.util.Map;

/**
 * Infos du potager [Solo, Eleveur , Equipe]
 * Note : depuis la V1.92 le potager est décomposé : garden/get, garden/get-composition-opponents, garden/get-farmer-challenge/n garden/get-farmer-opponents, garden/get-leek-opponents
 * @author Bezout
 * @version 1.1
 */
public class Garden {

	// SOLO
	private Map<String, Integer> solo_fights;
	private int total_solo_fights;
	private int max_solo_fights;

	// FARMER
	private boolean farmer_enabled;
	private int farmer_fights;
	private int farmer_total_fights;
	private int max_farmer_fights;

	// TEAM
	private boolean team_enabled;
	private int team_fights;
	private int team_total_fights;
	private int max_team_fights;
	private GardenFarmerTeamComposition[] my_compositions;

	// BATTLE ROYALE (since LW_1.92)
	private boolean battle_royale_enabled;
	private int battle_royale_fights;
	private int max_battle_royale_fights;
	
	//private Map<String, LeekSummary[]> solo_enemies;
	//private List<FarmerSummary> farmer_enemies;
	

	//private Map<String/* id de notre compo*/, GardenEnemyTeamComposition[]> enemies_compositions;
	

	// my_team

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


	public boolean isBattle_royale_enabled() {
		return battle_royale_enabled;
	}
	/**
	 * @param pBattle_royale_enabled the battle_royale_enabled to set
	 */
	public void setBattle_royale_enabled(boolean pBattle_royale_enabled) {
		battle_royale_enabled = pBattle_royale_enabled;
	}

	/**
	 * @return the max_solo_fights
	 */
	public int getMax_solo_fights() {
		return max_solo_fights;
	}
	/**
	 * @param pMax_solo_fights the max_solo_fights to set
	 */
	public void setMax_solo_fights(int pMax_solo_fights) {
		max_solo_fights = pMax_solo_fights;
	}

	/**
	 * @return the max_farmer_fights
	 */
	public int getMax_farmer_fights() {
		return max_farmer_fights;
	}
	/**
	 * @param pMax_farmer_fights the max_farmer_fights to set
	 */
	public void setMax_farmer_fights(int pMax_farmer_fights) {
		max_farmer_fights = pMax_farmer_fights;
	}

	/**
	 * @return the max_team_fights
	 */
	public int getMax_team_fights() {
		return max_team_fights;
	}
	/**
	 * @param pMax_team_fights the max_team_fights to set
	 */
	public void setMax_team_fights(int pMax_team_fights) {
		max_team_fights = pMax_team_fights;
	}

	/**
	 * @return the battle_royale_fights
	 */
	public int getBattle_royale_fights() {
		return battle_royale_fights;
	}
	/**
	 * @param pBattle_royale_fights the battle_royale_fights to set
	 */
	public void setBattle_royale_fights(int pBattle_royale_fights) {
		battle_royale_fights = pBattle_royale_fights;
	}

	/**
	 * @return the max_battle_royale_fights
	 */
	public int getMax_battle_royale_fights() {
		return max_battle_royale_fights;
	}
	/**
	 * @param pMax_battle_royale_fights the max_battle_royale_fights to set
	 */
	public void setMax_battle_royale_fights(int pMax_battle_royale_fights) {
		max_battle_royale_fights = pMax_battle_royale_fights;
	}
//	/**
//	 * @return the solo_fights
//	 */
//	public int getSolo_fights() {
//		return solo_fights;
//	}
//	/**
//	 * @param pSolo_fights the solo_fights to set
//	 */
//	public void setSolo_fights(int pSolo_fights) {
//		solo_fights = pSolo_fights;
//	}

	/**
	 * @return the solo_total_fights
	 */
	public int getTotal_solo_fights() {
		return total_solo_fights;
	}
	/**
	 * @param pSolo_total_fights the solo_total_fights to set
	 */
	public void setTotal_solo_fights(int pSolo_total_fights) {
		total_solo_fights = pSolo_total_fights;
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

//	/**
//	 * @return the solo_enemies
//	 */
//	public Map<String, LeekSummary[]> getSolo_enemies() {
//		return solo_enemies;
//	}
//	/**
//	 * @param pSolo_enemies the solo_enemies to set
//	 */
//	public void setSolo_enemies(Map<String, LeekSummary[]> pSolo_enemies) {
//		solo_enemies = pSolo_enemies;
//	}
//
//	/**
//	 * @return the farmer_enemies
//	 */
//	public List<FarmerSummary> getFarmer_enemies() {
//		return farmer_enemies;
//	}
//	/**
//	 * @param pFarmer_enemies the farmer_enemies to set
//	 */
//	public void setFarmer_enemies(List<FarmerSummary> pFarmer_enemies) {
//		farmer_enemies = pFarmer_enemies;
//	}

	/**
	 * @return the solo_fights
	 */
	public Map<String, Integer> getSolo_fights() {
		return solo_fights;
	}
	/**
	 * @param pLeek_fights the solo_fights to set
	 */
	public void setSolo_fights(Map<String, Integer> pLeek_fights) {
		solo_fights = pLeek_fights;
	}

	/**
	 * @return the my_compositions
	 */
	public GardenFarmerTeamComposition[] getMy_compositions() {
		return my_compositions;
	}
	/**
	 * Accès direct à une compo du tableau my_compositions
	 * @param pId
	 * @return compo
	 */
	public GardenFarmerTeamComposition getMyComposition(long pId) {
		if (my_compositions == null) {
			return null;
		}
		for (GardenFarmerTeamComposition lCompo : my_compositions) {
			if (lCompo.getId() == pId) {
				return lCompo;
			}
		}
		return null;
	}
	/**
	 * @param pMy_compositions the my_compositions to set
	 */
	public void setMy_compositions(GardenFarmerTeamComposition[] pMy_compositions) {
		my_compositions = pMy_compositions;
	}

//	/**
//	 * @return the enemies_compositions
//	 */
//	public Map<String, GardenEnemyTeamComposition[]> getEnemies_compositions() {
//		return enemies_compositions;
//	}
//	/**
//	 * @param pEnemies_compositions the enemies_compositions to set
//	 */
//	public void setEnemies_compositions(Map<String, GardenEnemyTeamComposition[]> pEnemies_compositions) {
//		enemies_compositions = pEnemies_compositions;
//	}
}
