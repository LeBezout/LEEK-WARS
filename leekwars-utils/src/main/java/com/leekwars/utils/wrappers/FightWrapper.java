package com.leekwars.utils.wrappers;

import com.leekwars.utils.enums.EntityType;
import com.leekwars.utils.model.Entity;

/**
 * Wrapper pour infos réduites d'un combat.
 * @author Bezout
 */
public class FightWrapper {
	private long fightId;
	private EntityType entityType = EntityType.LEEK;
	private Entity entity;
	private String fightResult;
	private String fightType;
	private String enemyName;
	private int turnCount;
	
	public FightWrapper(final Entity pEntity, final String pFightType, final long pId, final String pEnemyName) {
		fightId = pId;
		entity = pEntity;
		fightType = pFightType;
		enemyName = pEnemyName;
	}
	
	@Override
	public String toString() {
		return "COMBAT " + fightType + " " + fightId + " [" + (entity == null? "?" : entity.getName()) + " vs " + enemyName + "]";
	}
	
	/**
	 * @return the fightId
	 */
	public long getFightId() {
		return fightId;
	}
	/**
	 * @param pFightId the fightId to set
	 */
	public void setFightId(long pFightId) {
		fightId = pFightId;
	}
	/**
	 * @return the entity
	 */
	public Entity getEntity() {
		return entity;
	}
	/**
	 * @param pEntity the entity to set
	 */
	public void setEntity(Entity pEntity) {
		entity = pEntity;
	}
	/**
	 * @return the fightResult
	 */
	public String getFightResult() {
		return fightResult;
	}
	/**
	 * @param pFightResult the fightResult to set
	 */
	public void setFightResult(String pFightResult) {
		fightResult = pFightResult;
	}
	/**
	 * @return the fightType
	 */
	public String getFightType() {
		return fightType;
	}
	/**
	 * @param pFightType the fightType to set
	 */
	public void setFightType(String pFightType) {
		fightType = pFightType;
	}
	/**
	 * @return the enemyName
	 */
	public String getEnemyName() {
		return enemyName;
	}
	/**
	 * @param pEnemyName the enemyName to set
	 */
	public void setEnemyName(String pEnemyName) {
		enemyName = pEnemyName;
	}
	/**
	 * @return the turnCount
	 */
	public int getTurnCount() {
		return turnCount;
	}
	/**
	 * @param pTurnCount the turnCount to set
	 */
	public void setTurnCount(int pTurnCount) {
		turnCount = pTurnCount;
	}
	/**
	 * @return the entityType
	 */
	public EntityType getEntityType() {
		return entityType;
	}
	/**
	 * @param pEntityType the entityType to set
	 */
	public void setEntityType(EntityType pEntityType) {
		entityType = pEntityType;
	}
}
