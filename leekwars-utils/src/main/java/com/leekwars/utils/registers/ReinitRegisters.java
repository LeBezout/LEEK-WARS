package com.leekwars.utils.registers;

import com.leekwars.utils.AbstractLeekWarsConnector;
import com.leekwars.utils.LWConst;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.LeekSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe outils pour réinitialisation des registres de poireaux
 * @author Bezout
 */
public abstract class ReinitRegisters {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReinitRegisters.class.getName());

	/**
	 * @param pConnector
	 * @param pLeekID
	 * @throws LWException
	 */
	public static void reinitForLeek(final AbstractLeekWarsConnector pConnector, final long pLeekID) throws LWException {
		LOGGER.info(LWConst.LOG_SEPARATOR);
		pConnector.deleteAllRegisters(pLeekID);
		LOGGER.info(LWConst.LOG_SEPARATOR);
	}
	/**
	 * @param pConnector
	 * @param pLeekName
	 * @throws LWException
	 */
	public static void reinitForLeek(final AbstractLeekWarsConnector pConnector, final String pLeekName) throws LWException {
		LOGGER.info(LWConst.LOG_SEPARATOR);
		pConnector.deleteAllRegisters(pConnector.getLeekByName(pLeekName).getId());
		LOGGER.info(LWConst.LOG_SEPARATOR);
	}

	/**
	 * Réinitialise tous les registres des poireaux de l'éleveur.
	 * @param pConnector
	 * @throws LWException
	 */
	public static void reinitAll(final AbstractLeekWarsConnector pConnector) throws LWException {
		// récupération du token si besoin
		pConnector.connectIfNeeded();
		final Farmer lFarmer = pConnector.getFarmer();
		LOGGER.info(LWConst.LOG_SEPARATOR);
		LOGGER.info("Reinitialisation des registres de tous les poireaux de " + lFarmer);
		// Pour chaque poireau de l'éléveur
		for (LeekSummary lLeek : lFarmer.getLeeks().values()) {
			LOGGER.info("\tReinitialisation registres " + lLeek);
			pConnector.deleteAllRegisters(lLeek.getId());
		}
		LOGGER.info("Reinitialisation OK");
		LOGGER.info(LWConst.LOG_SEPARATOR);
	}
}
