package com.leekwars.utils.registers;

import org.apache.log4j.Logger;

import com.leekwars.utils.AbstractLeekWarsConnector;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.LeekSummary;

/**
 * Classe outils pour réinitialisation des registres de poireaux
 * @author Bezout
 */
public abstract class ReinitRegisters {
	private static final Logger LOGGER = Logger.getLogger(ReinitRegisters.class.getName());

	/**
	 * @param pConnector
	 * @param pLeekID
	 * @throws LWException
	 */
	public static void reinitForLeek(final AbstractLeekWarsConnector pConnector, final long pLeekID) throws LWException {
		LOGGER.info("----------------------------------------------------------------------------------");
		pConnector.deleteAllRegisters(pLeekID);
		LOGGER.info("----------------------------------------------------------------------------------");
	}
	/**
	 * @param pConnector
	 * @param pLeekName
	 * @throws LWException
	 */
	public static void reinitForLeek(final AbstractLeekWarsConnector pConnector, final String pLeekName) throws LWException {
		LOGGER.info("----------------------------------------------------------------------------------");
		pConnector.deleteAllRegisters(pConnector.getLeekByName(pLeekName).getId());
		LOGGER.info("----------------------------------------------------------------------------------");
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
		LOGGER.info("----------------------------------------------------------------------------------");
		LOGGER.info("Reinitialisation des registres de tous les poireaux de " + lFarmer);
		// Pour chaque poireau de l'éléveur
		for (LeekSummary lLeek : lFarmer.getLeeks().values()) {
			LOGGER.info("\tReinitialisation registres " + lLeek);
			pConnector.deleteAllRegisters(lLeek.getId());
		}
		LOGGER.info("Reinitialisation OK");
		LOGGER.info("----------------------------------------------------------------------------------");
	}
}
