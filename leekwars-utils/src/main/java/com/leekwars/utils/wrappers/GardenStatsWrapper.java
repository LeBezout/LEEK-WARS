package com.leekwars.utils.wrappers;

import java.text.DecimalFormat;

/**
 * Wrapper pour stoker les statisques de lancement des combats pour une entité : leek ou farmer
 * @author Bezout
 */
public class GardenStatsWrapper {

	private String entityName;
	private int totalFight;
	private int draws;
	private int victories;
	private int defeats;
	
	public GardenStatsWrapper(final String pEntityName) {
		super();
		entityName = pEntityName;
	}
	
//	public GardenStatsWrapper(final String pEntityName, final int pTotalFight, final int pDraws, final int pSuccess) {
//		this(pEntityName);
//		totalFight = pTotalFight;
//		victories = pSuccess;
//		draws = pDraws;
//	}
	
	@Override
	public String toString() {
		return entityName + " : " 
				+ victories + " victoires, "
				+ draws + " nuls, "
				+ defeats + " défaites"
				+ " sur " + totalFight + " combats,"
				+ " soit un ratio de " + new DecimalFormat("0.00").format(getRatio()) + " et " + getVictoriesPercent() + "% de réussite.";
	}
	
	public int getDefeats() {
		return defeats;
	}
	
	public GardenStatsWrapper incTotalFights() {
		totalFight++;
		return this;
	}
	public GardenStatsWrapper incVictories() {
		victories++;
		return this;
	}
	public GardenStatsWrapper incDefeats() {
		defeats++;
		return this;
	}
	public GardenStatsWrapper incDraws() {
		draws++;
		return this;
	}
	
	public int getVictoriesPercent() {
		return totalFight == 0 ? 0 : Math.round(100f * ((float)victories / (float)totalFight));
	}
	
	public float getRatio() {
		if (totalFight == 0 || victories == 0) {
			return 0f;
		}
		if (defeats == 0) {
			return (float)victories;
		}
		//  Nombre victoires/Nombre défaites
		return ((float)victories / (float)defeats);
	}

	/**
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * @return the totalFight
	 */
	public int getTotalFight() {
		return totalFight;
	}
	/**
	 * @param pTotalFight the totalFight to set
	 */
	public void setTotalFight(int pTotalFight) {
		totalFight = pTotalFight;
	}

	/**
	 * @return the draws
	 */
	public int getDraws() {
		return draws;
	}
	/**
	 * @param pDraws the draws to set
	 */
	public void setDraws(int pDraws) {
		draws = pDraws;
	}

	/**
	 * @return the victories
	 */
	public int getVictories() {
		return victories;
	}
	/**
	 * @param pSuccess the victories to set
	 */
	public void setVictories(int pSuccess) {
		victories = pSuccess;
	}
}
