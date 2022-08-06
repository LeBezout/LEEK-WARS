package com.leekwars.utils.io;

import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.SimpleJSONResponse;

/**
 * Informations recues si l'authentification s'est bien déroulée suite à un appel à "farmer/login-token".
 * @author Bezout
 */
public class LoginJSONResponse extends SimpleJSONResponse {
	private Farmer farmer;
	//private String token; // supprimé en 2.31.0
    // farmers: int
    // message: String
    // notifications[]
    // conversations[]
    // chats[]

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
