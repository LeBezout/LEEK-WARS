package com.leekwars.utils.wrappers;

import java.text.DecimalFormat;

import com.leekwars.utils.enums.EntityType;
import com.leekwars.utils.model.Entity;

/**
 * Wrapper pour stoker les statisques de lancement des combats pour une entité : leek ou farmer
 * @author Bezout
 */
public class GardenStatsWrapper {

	private Entity entity;
	private EntityType type;
	private int totalFight;
	private int draws;
	private int victories;
	private int defeats;
	
	private int initialTalent;
	private int finalTalent;
	
	public GardenStatsWrapper(final EntityType pType, final Entity pEntity) {
		super();
		type = pType;
		entity = pEntity;
		initialTalent = pEntity.getTalent();
	}
	
//	public GardenStatsWrapper(final String pEntityName, final int pTotalFight, final int pDraws, final int pSuccess) {
//		this(pEntityName);
//		totalFight = pTotalFight;
//		victories = pSuccess;
//		draws = pDraws;
//	}
	
	@Override
	public String toString() {
		return entity +  "(" + type + ") : " 
				+ victories + " victoires, "
				+ draws + " nuls, "
				+ defeats + " défaites"
				+ " sur " + totalFight + " combats,"
				+ " soit un ratio de " + new DecimalFormat("0.00").format(getRatio()) + " et " + getVictoriesPercent() + "% de réussite.";
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
	 * @return defeats
	 */
	public int getDefeats() {
		return defeats;
	}
	/**
	 * @param pDefeats the defeats to set
	 */
	public void setDefeats(int pDefeats) {
		defeats = pDefeats;
	}

	/**
	 * @return the entity
	 */
	public Entity getEntity() {
		return entity;
	}
	/**
	 * @return the entity
	 */
	public EntityType getEntityType() {
		return type;
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

	/**
	 * @return the initialTalent
	 */
	public int getInitialTalent() {
		return initialTalent;
	}
	/**
	 * @param pInitialTalent the initialTalent to set
	 */
	public void setInitialTalent(int pInitialTalent) {
		initialTalent = pInitialTalent;
	}

	/**
	 * @return the finalTalent
	 */
	public int getFinalTalent() {
		return finalTalent;
	}
	/**
	 * @param pFinalTalent the finalTalent to set
	 */
	public void setFinalTalent(int pFinalTalent) {
		finalTalent = pFinalTalent;
	}
	
	/**
	 * @return gain (+ ou -) en talent
	 */
	public int getTalentGain() {
		return finalTalent > 0 ? (finalTalent - initialTalent) : 0;
	}
}
