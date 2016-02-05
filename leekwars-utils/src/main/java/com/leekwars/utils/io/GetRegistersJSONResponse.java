package com.leekwars.utils.io;

import com.leekwars.utils.model.KeyValueCouple;
import com.leekwars.utils.model.SimpleJSONResponse;

/**
 * Informations des registres d'un poireau suite à un appel à "leek/get-registers"
 * @author Bezout
 */
public class GetRegistersJSONResponse extends SimpleJSONResponse {
	private KeyValueCouple[] registers;

	/**
	 * @return the registers
	 */
	public KeyValueCouple[] getRegisters() {
		return registers == null ? new KeyValueCouple[0] : registers;
	}

	/**
	 * @param pRegisters the registers to set
	 */
	public void setRegisters(KeyValueCouple[] pRegisters) {
		registers = pRegisters;
	}
	
}
