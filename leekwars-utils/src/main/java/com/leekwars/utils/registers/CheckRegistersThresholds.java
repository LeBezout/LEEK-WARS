package com.leekwars.utils.registers;

import com.leekwars.utils.AbstractLeekWarsConnector;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.KeyValueCouple;
import com.leekwars.utils.model.LeekSummary;

/**
 * Classe outils pour détections le remplissages des registres. Pour info :<ul>
 * <li>Les registres sont limités à 100 éléments.</li>
 * <li>La clé, chaîne qui doit contenir 100 caractères au maximum.</li>
 * <li>La valeur, chaîne qui doit contenir 5000 caractères au maximum.</li>
 * </ul>
 * @author Bezout
 */
public abstract class CheckRegistersThresholds {
	
	/** capacité maximul des registres pour 1 poireau */
	public static final int REGISTERS_MAX_CAPACITY = 100;

	/**
	 * Méthode de parcours des registres de tous les poireaux de l'eleveur concerné
	 * @param pConnector
	 * @param pVisitor
	 * @throws LWException
	 */
	public static void checkAllRegisters(final AbstractLeekWarsConnector pConnector, final RegistersVisitor pVisitor) throws LWException {
		// récupération du token si besoin
		pConnector.connectIfNeeded();
		final Farmer lFarmer = pConnector.getFarmer();
		pVisitor.onInit(lFarmer);
		try {
			// Pour chaque poireau de l'éléveur
			KeyValueCouple[] lRegisters;
			for (LeekSummary lLeek : lFarmer.getLeeks().values()) {
				lRegisters = pConnector.getRegisters(lLeek.getId());
				pVisitor.onRegisters(lLeek, lRegisters);
				if ((REGISTERS_MAX_CAPACITY - lRegisters.length) < pVisitor.getLeniency()) {
					pVisitor.onOverflow(lLeek);
				}
			}
		} finally {
			pVisitor.onEnd();
		}
	}
}
