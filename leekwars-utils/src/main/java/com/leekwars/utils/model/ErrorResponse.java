package com.leekwars.utils.model;

/**
 * Réponse JSON en cas d'erreur
 * @author Bezout
 * @since 1.6 (LW 2.0.2)
 */
public class ErrorResponse extends SimpleJSONResponse {

	private String error;

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}
	/**
	 * @param pError the error to set
	 */
	public void setError(String pError) {
		error = pError;
	}
	
}
