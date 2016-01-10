package com.leekwars.utils.io;

import com.leekwars.utils.model.Garden;
import com.leekwars.utils.model.SimpleJSONResponse;

/**
 * Informations du potager suite à un appel à "garden/get"
 * @author Bezout
 */
public class GardenJSONResponse extends SimpleJSONResponse {
	private Garden garden;

	/**
	 * @return the garden
	 */
	public Garden getGarden() {
		return garden;
	}

	/**
	 * @param pGarden the garden to set
	 */
	public void setGarden(Garden pGarden) {
		garden = pGarden;
	}
}
