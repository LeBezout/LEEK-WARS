package com.leekwars.utils.io;

import com.leekwars.utils.model.Fight;
import com.leekwars.utils.model.SimpleJSONResponse;

/**
 * Informations sur un combat suite à un appel à "fight/get".
 * @author Bezout
 */
public class GetFightJSONResponse extends SimpleJSONResponse {
	private Fight fight;

	/**
	 * @return the fight
	 */
	public Fight getFight() {
		return fight;
	}

	/**
	 * @param pFight the fight to set
	 */
	public void setFight(Fight pFight) {
		fight = pFight;
	}

}
