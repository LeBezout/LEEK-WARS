package com.leekwars.utils.io;

import com.leekwars.utils.model.SimpleJSONResponse;

/**
 * Porte le numéro du combat lorsque l'on démarre un combat suite à un appel à "garden/start-*-fight".
 * @author Bezout
 */
public class StartFightJSONResponse extends SimpleJSONResponse {
	private long fight;

	/**
	 * @return the fight
	 */
	public long getFight() {
		return fight;
	}

	/**
	 * @param pFight the fight to set
	 */
	public void setFight(long pFight) {
		fight = pFight;
	}
}
