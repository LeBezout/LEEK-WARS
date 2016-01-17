package com.leekwars.utils.fastgarden.impl;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.leekwars.utils.enums.EntityType;
import com.leekwars.utils.enums.FightResult;
import com.leekwars.utils.fastgarden.FastGardenVisitor;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.Fight;
import com.leekwars.utils.wrappers.GardenStatsWrapper;
import com.leekwars.utils.wrappers.MessageWrapper;

/**
 * Implementation de FastGardenVisitor qui loggue.
 * @author Bezout
 */
public class LoggerFastGardenVisitor implements FastGardenVisitor {
	private final Logger mLogger;
	private Level mLevel = Level.INFO;
	
	/**
	 * Constructeur avec logger
	 * @param pLogger
	 */
	public LoggerFastGardenVisitor(final Logger pLogger) {
		mLogger = pLogger;
	}
	/**
	 * Constructeur avec nom de logger
	 * @param pLoggerName
	 */
	public LoggerFastGardenVisitor(final String pLoggerName) {
		mLogger = Logger.getLogger(pLoggerName);
	}
	/**
	 * Constructeur avec nom de classe
	 * @param pClass
	 */
	public LoggerFastGardenVisitor(final Class<?> pClass) {
		mLogger = Logger.getLogger(pClass.getName());
	}
	
	/**
	 * Positionne le niveau de log souhaité (INFO par défaut)
	 * @param pLevel niveau/priorité
	 * @return this pour chainage
	 */
	public LoggerFastGardenVisitor setLevel(final Level pLevel) {
		mLevel = pLevel;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onInit(com.leekwars.utils.model.Farmer)
	 */
	@Override
	public void onInit(Farmer pFarmer) {
		mLogger.log(mLevel, "----------------------------------------------------------------------------------");
		mLogger.log(mLevel, String.format("Debut des combats pour %d %s", pFarmer.getId(), pFarmer.getName()));
		mLogger.log(mLevel, "----------------------------------------------------------------------------------");
	}

	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onMessage(com.leekwars.utils.wrappers.MessageWrapper)
	 */
	@Override
	public void onMessage(final MessageWrapper pMessage) {
		String lEntityName = pMessage.getEntity() == null ? "GENERAL" : pMessage.getEntity().getName();
		mLogger.warn(lEntityName + " : " + pMessage.getMessageFR());
	}

	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onEntityChange(com.leekwars.utils.enums.EntityType, java.lang.String)
	 */
	@Override
	public void onEntityChange(EntityType pEntityType, String pEntityName) {
		mLogger.log(mLevel, "-------------------------------------------------------------");
		if (pEntityType == EntityType.FARMER) {
			mLogger.log(mLevel, "Résultats pour l'éleveur " + pEntityName);
		} else if (pEntityType == EntityType.LEEK) {
			mLogger.log(mLevel, "Résultats pour le poireau " + pEntityName);
		}
		mLogger.log(mLevel, "-------------------------------------------------------------");
	}

	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onResult(com.leekwars.utils.model.Fight, com.leekwars.utils.enums.FightResult)
	 */
	@Override
	public void onResult(final Fight pFight, final FightResult pResult) {
		if (pResult == FightResult.UNKNOWN) {
			mLogger.log(mLevel, pFight.toString() + " -- pas de résultat");
		} else {
			mLogger.log(mLevel, pFight.toString() + " : " + pResult + " en " + pFight.getReport().getDuration() + " tours");
		}
	}

	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onBeforeStat()
	 */
	@Override
	public void onBeforeStat() {
		mLogger.log(mLevel, "-------------------------------------------------------------");
		mLogger.log(mLevel, " STATISTIQUES ");
		mLogger.log(mLevel, "-------------------------------------------------------------");
	}
	
	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onStat(com.leekwars.utils.wrappers.GardenStatsWrapper)
	 */
	@Override
	public void onStat(GardenStatsWrapper pStat) {
		mLogger.log(mLevel, pStat);
	}
	
	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onEnd()
	 */
	@Override
	public void onEnd() {
	}
}
