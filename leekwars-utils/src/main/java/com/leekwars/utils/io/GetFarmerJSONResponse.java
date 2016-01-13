package com.leekwars.utils.io;

import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.SimpleJSONResponse;

/**
 * Informations recues suite à un appel à "farmer/get".
 * @author Bezout
 */
public class GetFarmerJSONResponse extends SimpleJSONResponse {
	private Farmer farmer;
	
	/**
	 * @return the farmer
	 */
	public Farmer getFarmer() {
		return farmer;
	}
	/**
	 * @param pFarmer the farmer to set
	 */
	public void setFarmer(Farmer pFarmer) {
		farmer = pFarmer;
	}
}
