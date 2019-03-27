package com.leekwars.utils.model;

/**
 * Réponse JSON minimale
 * @author Bezout
 */
public class SimpleJSONResponse {

	private boolean success;
	private String error;

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param pSuccess the success to set
	 */
	public void setSuccess(boolean pSuccess) {
		success = pSuccess;
	}
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
