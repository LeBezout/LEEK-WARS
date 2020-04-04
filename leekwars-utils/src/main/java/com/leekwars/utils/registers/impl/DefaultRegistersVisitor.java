package com.leekwars.utils.registers.impl;

import com.leekwars.utils.LWConst;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.KeyValueCouple;
import com.leekwars.utils.model.LeekSummary;
import com.leekwars.utils.registers.CheckRegistersThresholds;
import com.leekwars.utils.registers.RegistersVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implémentation basique de type logging uniquement
 * @author Bezout
 */
public class DefaultRegistersVisitor implements RegistersVisitor {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRegistersVisitor.class.getName());

	@Override
	public int getLeniency() {
		return 10;
	}

	@Override
	public void onInit(Farmer pFarmer) {
		LOGGER.info(LWConst.LOG_SEPARATOR);
		LOGGER.info("Vérification du remplissage des registres de tous les poireaux de " + pFarmer);
	}

	@Override
	public void onLeek(LeekSummary pLeek) {
	}

	@Override
	public void onRegisters(LeekSummary pLeek, KeyValueCouple[] pRegisters) {
		LOGGER.info("Nombre de registres pour " + pLeek + " : " + pRegisters.length);
	}

	@Override
	public void onOverflow(LeekSummary pLeek) throws LWException {
		LOGGER.error(LWConst.LOG_WARN_SEPARATOR);
		LOGGER.error(pLeek + " : ATTENTION dépassement de taille acceptable des registres. Action à prévoir avant d'atteindre " + CheckRegistersThresholds.REGISTERS_MAX_CAPACITY + " registres.");
		LOGGER.error(LWConst.LOG_WARN_SEPARATOR);
	}

	@Override
	public void onEnd() {
		LOGGER.info(LWConst.LOG_SEPARATOR);
	}

}
