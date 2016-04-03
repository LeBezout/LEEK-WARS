package com.leekwars.utils.registers.impl;

import org.apache.log4j.Logger;

import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.KeyValueCouple;
import com.leekwars.utils.model.LeekSummary;
import com.leekwars.utils.registers.CheckRegistersThresholds;
import com.leekwars.utils.registers.RegistersVisitor;

/**
 * Implémentation basique de type logging uniquement
 * @author Bezout
 */
public class DefaultRegistersVisitor implements RegistersVisitor {
	private static final Logger LOGGER = Logger.getLogger(DefaultRegistersVisitor.class.getName());

	@Override
	public int getLeniency() {
		return 10;
	}

	@Override
	public void onInit(Farmer pFarmer) {
		LOGGER.info("----------------------------------------------------------------------------------");
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
		LOGGER.error("*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*");
		LOGGER.error(pLeek + " : ATTENTION dépassement de taille acceptable des registres. Action à prévoir avant d'atteindre " + CheckRegistersThresholds.REGISTERS_MAX_CAPACITY + " registres.");
		LOGGER.error("*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*~~*");
	}

	@Override
	public void onEnd() {
		LOGGER.info("----------------------------------------------------------------------------------");
	}

}
