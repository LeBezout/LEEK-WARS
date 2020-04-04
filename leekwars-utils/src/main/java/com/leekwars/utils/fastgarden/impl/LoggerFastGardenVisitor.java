package com.leekwars.utils.fastgarden.impl;

import com.leekwars.utils.LWConst;
import org.slf4j.Logger;

import com.leekwars.utils.enums.EntityType;
import com.leekwars.utils.enums.FightResult;
import com.leekwars.utils.fastgarden.FastGardenVisitor;
import com.leekwars.utils.model.Entity;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.Fight;
import com.leekwars.utils.wrappers.GardenStatsWrapper;
import com.leekwars.utils.wrappers.MessageWrapper;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

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
		mLogger = LoggerFactory.getLogger(pLoggerName);
	}
	/**
	 * Constructeur avec nom de classe
	 * @param pClass
	 */
	public LoggerFastGardenVisitor(final Class<?> pClass) {
		mLogger = LoggerFactory.getLogger(pClass.getName());
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
		log("----------------------------------------------------------------------------------");
		log(String.format("Debut des combats pour %d %s", pFarmer.getId(), pFarmer.getName()));
		log("----------------------------------------------------------------------------------");
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
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onEntityChange(com.leekwars.utils.enums.EntityType, com.leekwars.utils.model.Entity)
	 */
	@Override
	public void onEntityChange(EntityType pEntityType, Entity pEntity) {
		log(LWConst.LOG_SEPARATOR);
		if (pEntityType == EntityType.FARMER) {
			log("Résultats pour l'éleveur " + pEntity.getName());
		} else if (pEntityType == EntityType.LEEK) {
			log("Résultats pour le poireau " + pEntity.getName());
		}
		log(LWConst.LOG_SEPARATOR);
	}

	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onResult(com.leekwars.utils.model.Fight, com.leekwars.utils.enums.FightResult)
	 */
	@Override
	public void onResult(final Fight pFight, final FightResult pResult) {
		if (pResult == FightResult.UNKNOWN) {
			log(pFight.toString() + " -- pas de résultat");
		} else {
			log(pFight.toString() + " : " + pResult + " en " + pFight.getReport().getDuration() + " tours");
		}
	}

	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onBeforeStat()
	 */
	@Override
	public void onBeforeStat() {
		log(LWConst.LOG_SEPARATOR);
		log(" STATISTIQUES ");
		log(LWConst.LOG_SEPARATOR);
	}
	
	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onStat(com.leekwars.utils.wrappers.GardenStatsWrapper)
	 */
	@Override
	public void onStat(GardenStatsWrapper pStat) {
		log(pStat.toString());
	}
	
	/* (non-Javadoc)
	 * @see com.leekwars.utils.fastgarden.FastGardenVisitor#onEnd()
	 */
	@Override
	public void onEnd() {
		// rien à faire dans cette implémentation
	}
	
	private void log(String msg) {
	    switch (mLevel) {
            case TRACE: mLogger.trace(msg); return;
            case INFO: mLogger.info(msg); return;
            case WARN: mLogger.warn(msg); return;
            case ERROR: mLogger.error(msg); return;
            case DEBUG:
            default:
                mLogger.debug(msg);
        }
    }
}
